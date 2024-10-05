new DataTable('#example');
$('label[for="dt-length-0"]').text('Số hàng trên 1 trang')
$('label[for="dt-search-0"]').text('Tìm kiếm:')
$('#dt-search-0').attr('placeholder', '')

// Remove the div with id "example_info"
document.getElementById('example_info').remove();




// thay doi link trong nut them
var rowContainers = document.querySelectorAll('.row.mt-2.justify-content-between');

// Check if there are at least two such containers
if (rowContainers.length >= 2) {
    // Select the first .col-md-auto.me-auto container within the second row container
    var secondRowContainer = rowContainers[1];
    var firstColMdAutoMeAuto = secondRowContainer.querySelector('.col-md-auto.me-auto');

    // Append the new <a> element to the selected container
    if (firstColMdAutoMeAuto) {
        firstColMdAutoMeAuto.appendChild(newLink);
    } else {
        console.error("The '.col-md-auto.me-auto' element within the second row was not found.");
    }
} else {
    console.error("There are less than two '.row.mt-2.justify-content-between' elements.");
}


