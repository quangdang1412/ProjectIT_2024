$(document).ready(function () {
  // Load provinces dropdown on page load
  $.ajax({
    url: "http://localhost:8080/locations",
    type: "GET",
    success: function (data) {
      var provinces = [];
      var districts = {};
      var communes = {};

      // Process data to populate arrays
      data.forEach(function (location) {
        if (!provinces.includes(location.provinceName)) {
          provinces.push(location.provinceName);
        }

        // Initialize district array if not exists
        if (!districts[location.provinceName]) {
          districts[location.provinceName] = [];
        }
        if (!districts[location.provinceName].includes(location.districtName)) {
          districts[location.provinceName].push(location.districtName);
        }

        // Initialize commune array if not exists
        if (!communes[location.districtName]) {
          communes[location.districtName] = [];
        }
        if (!communes[location.districtName].includes(location.communeName)) {
          communes[location.districtName].push(location.communeName);
        }
      });

      // Populate provinces dropdown
      var provinceDropdown = $("#provinceDropdown");
      provinceDropdown.empty();
      provinceDropdown.append(
        '<option selected="true" disabled>Chọn tỉnh thành</option>'
      );
      provinces.forEach(function (province) {
        provinceDropdown.append(
          $("<option></option>").attr("value", province).text(province)
        );
      });

      // Initialize Select2 for provinces dropdown
      provinceDropdown.select2();

      // Handle province change event to load districts
      provinceDropdown.change(function () {
        var selectedProvince = $(this).val();
        var districtDropdown = $("#districtDropdown");
        districtDropdown.empty();
        districtDropdown.append(
          '<option selected="true" disabled>Choose thành phố/huyện </option>'
        );
        districts[selectedProvince].forEach(function (district) {
          districtDropdown.append(
            $("<option></option>").attr("value", district).text(district)
          );
        });

        // Refresh Select2 after updating options
        districtDropdown.select2("destroy").select2();
      });

      // Initialize Select2 for districts dropdown
      $("#districtDropdown").select2();

      // Handle district change event to load communes
      $("#districtDropdown").change(function () {
        var selectedDistrict = $(this).val();
        var communeDropdown = $("#communeDropdown");
        communeDropdown.empty();
        communeDropdown.append(
          '<option selected="true" disabled>Chọn phường/xã</option>'
        );
        communes[selectedDistrict].forEach(function (commune) {
          communeDropdown.append(
            $("<option></option>").attr("value", commune).text(commune)
          );
        });

        // Refresh Select2 after updating options
        communeDropdown.select2("destroy").select2();
      });

      // Initialize Select2 for communes dropdown
      $("#communeDropdown").select2();
    },
  });
});
