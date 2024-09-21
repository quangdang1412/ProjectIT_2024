
function sendSupplierData(check) {
    const user = getSupplierData();
    const method = check === 'insert' ? 'POST' : 'PUT';
    const endpoint = check === 'insert' ? 'add' : 'update';
    sendRequest(method,'supplier',endpoint, user, '/admin/Supplier');
}
function deleteSupplier(id) {
    sendRequest('DELETE','supplier' ,`delete/${id}`, id, 'delete');
}