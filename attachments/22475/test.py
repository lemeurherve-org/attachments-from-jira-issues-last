import win32file

def get_drivestats(drive):
    drive = drive.rstrip(':\\').rstrip(':/')
    sectPerCluster, bytesPerSector, freeClusters, totalClusters = \
        win32file.GetDiskFreeSpace(drive + ":\\")
    r = (
        (totalClusters*sectPerCluster*bytesPerSector),
        (freeClusters*sectPerCluster*bytesPerSector),
        ((totalClusters-freeClusters )*sectPerCluster*bytesPerSector)
        )
    
    return r

# pick a drive letter
drive = 'C'
# calculation
total_space, free_space, used_space = get_drivestats(drive)
ninetyfive_percent_space = (total_space*0.95)

#print("""
#total_space = %d bytes
#free_space = %d bytes
#used_space = %d bytes
#percentage_space = %d bytes""" % (total_space, free_space, used_space, ninetyfive_percent_space))

if (int(free_space) >= int(ninetyfive_percent_space)):
    print "Alert: 95% Diskspace Threshold Reached (" + str(free_space) + " bytes remaining). Contact Administrator."
else:
    print "Success: 95% Diskspace Threshold Not Reached."
