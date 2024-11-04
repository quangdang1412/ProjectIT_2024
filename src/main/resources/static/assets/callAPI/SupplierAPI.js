
function sendSupplierData(check) {
    const user = getSupplierData();
    const method = check === 'insert' ? 'POST' : 'PUT';
    const endpoint = check === 'insert' ? 'add' : 'update';
    sendRequest(method,'supplier',endpoint, user, '/admin/Supplier');
}
function deleteSupplier(id) {
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
            sendRequest('DELETE','supplier' ,`delete/${id}`, id, 'delete');
        }
    });
}