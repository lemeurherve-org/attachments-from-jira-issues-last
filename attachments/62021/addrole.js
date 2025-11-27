'use strict';

// noinspection JSUnusedGlobalSymbols
/**
 * Adds a global role
 */
const addGlobalRole = () => {
    const roleName = document.getElementById('globalRoleName').value;
    if (!roleName || roleName.length < 3) {
        alert('Please enter a valid name for the role to be added.');
        return;
    }

const permissionSelect = document.getElementById('global-permission-select');


const selectedPermissions = [];
for (let i = 0; i < permissionSelect.selectedOptions.length; i++) {
  selectedPermissions.push(permissionSelect.selectedOptions[i].value);
}


const response = {
  name: roleName,
  permissions: selectedPermissions,
};

    if (response.permissions.length <= 0) {
        alert('Please select at least one permission');
        return;
    }

    sendPostRequest(`${rootURL}/folder-auth/addGlobalRole`, response);
};

// noinspection JSUnusedGlobalSymbols
/**
 * Adds a Folder Role
 */
const addFolderRole = () => {
    const roleName = document.getElementById('folderRoleName').value;
    if (!roleName || roleName.length < 3) {
        alert('Please enter a valid name for the role to be added');
        return;
    }

const permissionSelect = document.getElementById('folder-permission-select');
const folderSelect = document.getElementById('folder-select');

const selectedPermissions = [];
for (let i = 0; i < permissionSelect.selectedOptions.length; i++) {
  selectedPermissions.push(permissionSelect.selectedOptions[i].value);
}

const selectedFolders = [];
for (let i = 0; i < folderSelect.selectedOptions.length; i++) {
  selectedFolders.push(folderSelect.selectedOptions[i].value);
}

const response = {
  name: roleName,
  permissions: selectedPermissions,
  folderNames: selectedFolders,
};

    if (!response.permissions || response.permissions.length <= 0) {
        alert('Please select at least one permission');
        return;
    }

    if (!response.folderNames || response.folderNames.length <= 0) {
        alert('Please select at least one folder on which this role will be applicable');
        return;
    }

    sendPostRequest(`${rootURL}/folder-auth/addFolderRole`, response);
};

// noinspection JSUnusedGlobalSymbols
/**
 * Adds an agent Role
 */
const addAgentRole = () => {
    const roleName = document.getElementById('agentRoleName').value;
    if (!roleName || roleName.length < 3) {
        alert('Please enter a valid name for the role to be added');
        return;
    }
const permissionSelect = document.getElementById('agent-permission-select');
const agentSelect = document.getElementById('agent-select');

const selectedPermissions = [];
for (let i = 0; i < permissionSelect.selectedOptions.length; i++) {
  selectedPermissions.push(permissionSelect.selectedOptions[i].value);
}

const selectedAgents = [];
for (let i = 0; i < agentSelect.selectedOptions.length; i++) {
  selectedAgents.push(agentSelect.selectedOptions[i].value);
}

const response = {
  name: roleName,
  permissions: selectedPermissions,
  agentNames: selectedAgents,
};

    if (!response.permissions || response.permissions.length <= 0) {
        alert('Please select at least one permission');
        return;
    }

    if (!response.agentNames || response.agentNames.length <= 0) {
        alert('Please select at least one agent on which this role will be applicable');
        return;
    }

    sendPostRequest(`${rootURL}/folder-auth/addAgentRole`, response);
};

/**
 * Sends a POST request to {@code postUrl}
 * @param postUrl the URL
 * @param json JSON data to be sent
 */
const sendPostRequest = (postUrl, json) => {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', postUrl, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    // Jelly file sets up the crumb value for CSRF protection
    if (crumb.value) {
        xhr.setRequestHeader('Jenkins-Crumb', crumb.value);
    }

    xhr.onload = () => {
        if (xhr.status === 200) {
            alert('The role was added successfully');
            location.reload(); // refresh the page
        } else {
            alert('Unable to add the role\n' + xhr.responseText);
        }
    };

    // this is really bad.
    // See https://github.com/jenkinsci/jenkins/blob/75468da366c1d257a51655dcbe952d55b8aeeb9c/war/src/main/js/util/jenkins.js#L22
    const oldPrototype = Array.prototype.toJSON;
    delete Array.prototype.toJSON;

    try {
        xhr.send(JSON.stringify(json));
    } finally {
        Array.prototype.toJSON = oldPrototype;
    }
};
