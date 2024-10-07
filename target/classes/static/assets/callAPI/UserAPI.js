function sendUserData(check) {
  const user = getUserData();
  const method = check === 'insert' ? 'POST' : 'PUT';
  const endpoint2 = check === 'insert' ? 'add' : 'update';
  sendRequest(method,'user',endpoint2, user, '/admin/User');
}
function changeUserPassword() {
  let data ={
oldPassword: getValue('oldpassword'),
    newPassword: getValue('newpassword'),
    confirmPassword: getValue('confirmpassword'),
  }
  sendRequest('PUT','user','changePassword',data,'/')
}
function deleteUser(id) {
  sendRequest('DELETE','user',`delete/${id}`, id, 'delete');
}

function updateInfoUser() {
  const user = {
    userID: getValue('UserID'),
    userName: getValue('UserName'),
    email: getValue('Email'),
    phone: getValue('Phone'),
    gender: getValue('Gender', 'select'),
    address: getValue('Address'),
  };
  sendRequest('PUT','user','updateInfo', user,'/');
}
