// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function(n, prec) {
      var k = Math.pow(10, prec);
      return '' + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

let dataTime = {};
let listTime = [];
let dataRe = [];
let top5 = [];
let dataTop5 = [];
let myLineChart;
let myPieChart;
$(document).ready(function() {
  // Thiết lập giá trị của EndDate là hôm nay
  let currentDate = new Date();
  let dd = String(currentDate.getDate()).padStart(2, '0');
  let mm = String(currentDate.getMonth() + 1).padStart(2, '0');
  let yyyy = currentDate.getFullYear();

  let startDate = new Date(currentDate);
  startDate.setDate(currentDate.getDate() - 30);
  let startDd = String(startDate.getDate()).padStart(2, '0');
  let startMm = String(startDate.getMonth() + 1).padStart(2, '0');
  let startYyyy = startDate.getFullYear();

  currentDate = yyyy + '-' + mm + '-' + dd;
  startDate = startYyyy + '-' + startMm + '-' + startDd;

  document.getElementById("EndDate").value = currentDate;
  document.getElementById("StartDate").value = startDate;
  sendDashboardData()
});
function sendDashboardData(){
  // Area Chart Example
  dataTime = {
    startDate: getValue("StartDate"),
    endDate: getValue("EndDate")
  };
  $.ajax({
    url: "/api/dashboard/chartArea",
    method: "POST",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("token"),
    },
    contentType: "application/json",
    data: JSON.stringify(dataTime),
    success: function (response) {
      listTime = response.data.time;
      dataRe = response.data.data;
      top5=response.data.top5;
      dataTop5=response.data.dataTop5;
      chartArea(listTime,dataRe,top5,dataTop5)
    },
    error: function (xhr) {
      // Xử lý lỗi khi có lỗi
      let errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại!";
      console.log(errorMessage);
    }
  })
}

function chartArea(listTime, dataRe,top5,dataTop5) {
  var ctx = document.getElementById("myAreaChart");
  if (myLineChart) {
    myLineChart.destroy();
  }

  myLineChart = new Chart(ctx, {
    type: 'line',

    data: {
      labels: listTime,
      datasets: [{
        label: "Doanh thu",
        lineTension: 0.3,
        backgroundColor: "rgba(78,115,223,0.13)",
        borderColor: "rgba(78, 115, 223, 1)",
        pointRadius: 3,
        pointBackgroundColor: "rgba(78, 115, 223, 1)",
        pointBorderColor: "rgba(78, 115, 223, 1)",
        pointHoverRadius: 3,
        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
        pointHitRadius: 10,
        pointBorderWidth: 2,
        data: dataRe,
      }],
    },
    options: {
      maintainAspectRatio: false,
      layout: {
        padding: {
          left: 10,
          right: 25,
          top: 25,
          bottom: 0
        }
      },
      scales: {
        xAxes: [{
          time: {
            unit: 'date'
          },
          gridLines: {
            display: false,
            drawBorder: false
          },
          ticks: {
            maxTicksLimit: 7
          }
        }],
        yAxes: [{
          ticks: {
            maxTicksLimit: 5,
            padding: 10,
            callback: function(value, index, values) {
              return number_format(value)+ ' VND';
            }
          },
          gridLines: {
            color: "rgb(234, 236, 244)",
            zeroLineColor: "rgb(234, 236, 244)",
            drawBorder: false,
            borderDash: [2],
            zeroLineBorderDash: [2]
          }
        }],
      },
      legend: {
        display: false
      },
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        fontSize: 10,
        titleMarginBottom: 10,
        titleFontColor: '#6e707e',
        titleFontSize: 15,
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        intersect: false,
        mode: 'index',
        caretPadding: 10,
        callbacks: {
          label: function(tooltipItem, chart) {
            var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
            return datasetLabel + ': ' + number_format(tooltipItem.yLabel)+' VND';
          }
        }
      }
    }
  });
  // chart Pie
  ctx = document.getElementById("myPieChart");
  if (myPieChart) {
    myPieChart.destroy();
  }
  myPieChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: top5,
      datasets: [{
        data: dataTop5,
        backgroundColor: [ '#1cc88a','#4e73df', '#36b9cc','#f6c23e','#e74a3b'],
        hoverBackgroundColor: ['#17a673','#2e59d9', '#2c9faf'],
        hoverBorderColor: "rgba(234, 236, 244, 1)",
      }],
    },
    options: {
      maintainAspectRatio: false,
      tooltips: {
        backgroundColor: "rgb(255,255,255)",
        bodyFontColor: "#858796",
        borderColor: '#dddfeb',
        borderWidth: 1,
        xPadding: 15,
        yPadding: 15,
        displayColors: false,
        caretPadding: 10,
      },
      legend: {
        display: false
      },
      cutoutPercentage: 80,
    },
  });
  top5.forEach((item, index) => {
    const spanElement = document.getElementById(`top${index + 1}Pie`);

    spanElement.innerHTML = `<i class="${spanElement.querySelector('i').className}"></i>`;

    spanElement.querySelector('i').insertAdjacentText('afterend', ` ${item}`);

    console.log(item);
  });
}

