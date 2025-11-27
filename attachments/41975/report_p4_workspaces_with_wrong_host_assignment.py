# ---------------------------------------------------------------------------
#
# report_p4_workspaces_with_wrong_host_assignment.py
#
# This code reports the P4 workspaces with a wrong host assigned.
#
# Author:  Heiko Nardmann
# Created: MAR-2018
#
# ---------------------------------------------------------------------------

import os, sys
sys.path.append(os.path.join(os.path.dirname(os.path.dirname(__file__)), "lib"))

def reportP4WorkspacesWithWrongHostAssigned():
    from P4 import ( P4, P4Exception )
    p4 = P4()
    p4.port = "..."
    p4.user = "..."
    p4.password = "..."

    p4.connect()
    clientSpecPattern = "jenkins_frinavci_*"
    try:
        clients = p4.run( "clients", "-e", clientSpecPattern )
    except P4Exception as e:
        print("e=" + str(e))
        return
    for eachClient in clients:
        if eachClient['client'].startswith('jenkins_frinavci_outer_workspace'):
            hostnameUsedInsideClientName = eachClient['client'][len('jenkins_frinavci_outer_workspace_'):]
            if hostnameUsedInsideClientName.lower() != eachClient['Host'].lower():
                print("{0}: {1}".format(eachClient['client'], eachClient['Host']))
            continue
        elif eachClient['client'].startswith('jenkins_frinavci_JNLP4'):
            # ignore this one
            continue
        elif eachClient['client'].startswith('jenkins_frinavci_master_'):
            if eachClient['Host'] and eachClient['Host'] != 'master':
                print("{0}: {1}".format(eachClient['client'], eachClient['Host']))
            continue
        else:
            if not eachClient['Host']:
                # if no host is given then ignore this one so far ...
                continue
            hostnameUsedInsideClientName = eachClient['client'][len('jenkins_frinavci_'):].split('_')[0]
            if hostnameUsedInsideClientName.lower() != eachClient['Host'].lower():
                # sth. has gone wrong here: why is there a hostname mismatch?
                print("{0}: {1}".format(eachClient['client'], eachClient['Host']))
            continue

if __name__ == "__main__":
    reportP4WorkspacesWithWrongHostAssigned()
    