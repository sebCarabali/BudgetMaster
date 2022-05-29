/* This list will be dynamically filled with all the transactions between
 * the start and and date you select on the "Show Chart" page
 * and filtered according to your specified filter.
 * An example entry for this list and tutorial about how to create custom charts ca be found in the BudgetMaster wiki:
 * https://github.com/deadlocker8/BudgetMaster/wiki/How-to-create-custom-charts
 */
var transactionData = [];

// Note: All variables starting with "localized" are only available inside default charts.
moment.locale(localizedLocale);

var dates = [];
var rests = [];

transactionData = transactionData.reverse();

for(var i = 0; i < transactionData.length; i++)
{
    var transaction = transactionData[i];

    // create new sums if month is not already in list
    var date = moment(transaction.date).startOf('month').format('MMM YY');
    if(!dates.includes(date))
    {
        dates.push(date);
        rests.push(0);
    }

    var lastIndex = rests.length - 1;
    rests[lastIndex] = rests[lastIndex] + transaction.amount;
}

// convert all sums to decimal
rests.forEach(function(value, index)
{
    this[index] = value / 100;
}, rests);

// Prepare your chart settings here (mandatory)
var plotlyData = [
    {
        x: dates,
        y: rests,
        type: 'bar',
    },
];

// Add your Plotly layout settings here (optional)
var plotlyLayout = {
    title: {
        text: formatChartTitle(localizedTitle, localizedDateRange),
    },
    yaxis: {
        title: localizedData['label1'] + localizedCurrency,
        rangemode: 'tozero',
        tickformat: '.2f',
        showline: true
    },
    xaxis: {
        tickformat: '%d.%m.%y'
    }
};

// Add your Plotly configuration settings here (optional)
var plotlyConfig = {
    showSendToCloud: false,
    displaylogo: false,
    showLink: false,
    responsive: true,
    displayModeBar: true,
    toImageButtonOptions: {
        format: 'png',
        filename: 'BudgetMaster_chart_export',
        height: 1080,
        width: 1920,
    }
};

// Don't touch this line
Plotly.newPlot("containerID", plotlyData, plotlyLayout, plotlyConfig);