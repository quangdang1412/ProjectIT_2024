function sendOrderData(check) {
    const order = check === 'insert' ? getPlaceOrderData() : getOrderData();
    const method = check === 'insert' ? 'POST' : 'PUT';
    const endpoint = check === 'insert' ? 'placeOrder' : 'update';
    const url = check === 'insert' ? '/' : '/admin/Order';
    sendRequest(method,'order',endpoint, order, url);
}
function updateOrderData() {
    const order = getOrderData();
    const method = 'PUT';
    const endpoint = 'update';
    const url = '/yourOrder' ;
    sendRequest(method,'order',endpoint, order, url);
}
function deleteOrder(id) {
    sendRequest('DELETE','order',`delete/${id}`, id, 'delete');
}