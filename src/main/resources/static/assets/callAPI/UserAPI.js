function sendUserData(check) {
  const user = getUserData();
  const method = check === 'insert' ? 'POST' : 'PUT';
  const endpoint2 = check === 'insert' ? 'add' : 'update';
  sendRequest(method,'user',endpoint2, user, '/admin/User');
}
function changeUserPassword(email) {
  if(email === 'null'){
    let data ={
      oldPassword: getValue('oldpassword'),
      newPassword: getValue('newpassword'),
      confirmPassword: getValue('confirmpassword'),
    }
    sendRequest('PUT','user','changePassword',data,'/')
  }
  else{
    let data ={
      email: email,
      newPassword: getValue('newpassword'),
      confirmPassword: getValue('confirmpassword'),
    }
      const xhr = new XMLHttpRequest();
      xhr.open("PUT", `http://localhost:8080/api/auth/resetPassword`, true);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function () {
        notify(xhr,"/",data)
      };
      xhr.send(JSON.stringify(data));
    }
}
function deleteUser(id) {
  const $link = $(`a[onclick*='${id}']`);
  const currentStatus = $link.text().trim();
  let str
  if (currentStatus === "Active")
    str ="kích hoạt"
  else
    str ="vô hiệu hóa"
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger"
    },
  });
  swalWithBootstrapButtons.fire({
    title: `Bạn có muốn ${str} không?`,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Yes",
    cancelButtonText: "No",
    reverseButtons: true
  }).then((result) => {
    if (result.isConfirmed) {
      sendRequest('DELETE','user',`delete/${id}`, id, 'delete');
    }
  });
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
function resetPassword() {
  Swal.fire({
    icon: "info",
    title: "Reset your password",
    input: "email",
    inputLabel: "Your email address",
    inputPlaceholder: "Enter your email address",
    showCancelButton: true,
    showLoaderOnConfirm: true,
    confirmButtonText: "Send",
    reverseButtons: true,
    preConfirm: (email) => {
      return new Promise((resolve, reject) => {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4) {
            if (this.status == 200) {
              resolve(this.responseText);  // Just pass the responseText
            } else {
              Swal.showValidationMessage(`Request failed: ${this.statusText}`);
              reject(this.statusText);
            }
          }
        };
        xhttp.open("POST", "/api/auth/forgot_password", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify({ email: email }));
      });
    }
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "Success!",
        text: result.value,  // Show the message returned by the server
        icon: "success"
      });
    }
  }).catch((error) => {
    Swal.fire({
      title: "Error!",
      text: error,
      icon: "error"
    });
  });
}

