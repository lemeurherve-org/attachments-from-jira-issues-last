import pandas as pd
import json ,sys ,re

excel_file_name = "Release_Template_10.04.00.xlsx"
phase_no = input("Enter phase number : ")

deployment_details_sheet = 'Upgrade Deployment Details'
common_build_param_sheet = 'Common Build Parameters'

print("[[ INFO :: Validating " + excel_file_name + " ]]")

def get_indexes(column_heading):
    if 'services' in column_heading:
        service_index = column_heading.index('services')
    else:
        print("ERROR!! : Service details not found")
        return False,[]
    
    if 'catalog' in column_heading:
        catalog_index = column_heading.index('catalog')
    else:
        print("ERROR!! : Catalog details not found")
        return False,[]
    
    if 'params' in column_heading:
        params_index = column_heading.index('params')
    else:
        print("ERROR!! : Params details not found")
        return False,[]

    return True,[service_index,catalog_index,params_index]

data = {'services' : '','version':''}


try:
    deploymentSheetdf = pd.read_excel(excel_file_name,sheet_name = deployment_details_sheet ,na_filter=False)
    paramSheetdf = pd.read_excel(excel_file_name,sheet_name = common_build_param_sheet,na_filter=False)
    column_heading =[elem.lower().strip() for elem in  list(deploymentSheetdf.columns)]
    status,headings = get_indexes(column_heading)
    
    if status == True:
        service_index,catalog_index,params_index = headings
    
    else:
        sys.exit()


    services =[]
    version = ''

    if len(deploymentSheetdf.values)>0:
        first_catalog = deploymentSheetdf.values[0][catalog_index]
        
        try:
            start = first_catalog.index('(')+1
            end = first_catalog.index(')')
            version =first_catalog[start:end]
            data['version'] = version
        except ValueError:
            print("ERROR!! : Version Not Properly Set in template")
            sys.exit()

    for row in deploymentSheetdf.values:

        service_name = row[service_index].strip()
        services.append(service_name)
        catalog = re.sub('\s+',row[catalog_index].strip(),' ')
        service_params = {}       

        for param in row[params_index].strip().split(','):
            param = param.split(':')           
            if len(param) == 2:
                param_key = param[0].strip()                
                if param_key not in service_params:
                    service_params[param_key] = param[1].strip()
                else:
                    print("ERROR!! : Repeated params key " + param_key+" for " + service_name)
                    sys.exit()
            else:
                print("ERROR!! : Invalid param key " + param_key+" for " + service_name)
                sys.exit()

        data[service_name] ={ 'catalog':catalog,'params':service_params}
        
        for col_index,cols in enumerate(list(row)):
            
            if col_index not in [service_index,params_index,catalog_index]  :
                col_name = column_heading[col_index]
                cols = cols.strip()
                column_dict = {}
                if cols != '':
                    
                    cols_list = cols.split(',')

                    for col in cols_list:
                        key_value = col.split(':')
                        if len(key_value) != 2:
                            print("ERROR!! : Invalid param " + cols+" for "+col_name +" in " + service_name)
                            sys.exit()
                        else:
                            key = key_value[0].strip()
                            if key not in column_dict:
                                column_dict[key]=key_value[1]
                            else:
                                print("ERROR!! : Repeated params key " + key+" for "+col_name+" in " + service_name)
                                sys.exit()

                    if len(column_dict) != 0:
                        data[service_name][col_name] =  column_dict

    data['services'] = ','.join(services)
    common_parameters = {}

    for param in paramSheetdf.values:
        key_value = param[0].strip().split(':')

        if len(key_value) == 2:
            key = key_value[0].replace(' ','')
            if key not in common_parameters:
                common_parameters[key] = key_value[1].replace(' ','')
            else:
                print("ERROR!! : Repeated params key "+key + " in Common Build Parameters" )
                sys.exit()
        else:
            print("ERROR!! : Invalid Key Value Pair "+ param + " in Common Build Parameters")
            sys.exit()

    data["common_parameter"] = common_parameters    
    json_data = json.dumps(data,indent=2)

    file_name = version+'-'+phase_no+'_'+'release.json'
    print("[[ INFO :: Creating " + file_name + " ]]")
    with open(file_name,'w') as file:
        file.write(json_data)
    print("[[ INFO :: Valid metadata " + file_name + " generated ]]")

except KeyError as e:
    print('ERROR!! : ',e)

except IndexError as e:
    print('ERROR!! : ',e)
    
except PermissionError as e:
    print('ERROR!! : ',e)

except FileNotFoundError as e:
    print('ERROR!! : ',e)