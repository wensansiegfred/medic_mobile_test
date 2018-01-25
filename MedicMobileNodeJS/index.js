"use strict";

var params = require("yargs").argv;
var FileUtil = require("./util/FileUtil");

var csvFile = params.csvFile;
var csvOutputFile = params.csvOutputFile || "output.csv";

if (csvFile) {
    var debtList = [];
    FileUtil.readCSVFile(csvFile, function(csvData) {
        if (csvData.length > 0) {
            for(var n in csvData) {
                if (debtList.length == 0) {
                    debtList.push({debtor: csvData[n][0], creditor: csvData[n][1], amount: parseFloat(csvData[n][2])});
                } else {
                    var exist = false;
                    for (var i in debtList) {
                        if (debtList[i].debtor == csvData[n][0] && debtList[i].creditor == csvData[n][1]) {
                            var newAmount = parseFloat(csvData[n][2]) + parseFloat(debtList[i].amount);
                            debtList[i].amount = newAmount;
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        debtList.push({debtor: csvData[n][0], creditor: csvData[n][1], amount: parseFloat(csvData[n][2])});
                    }
                }
            }
            debtList.sort(function(a, b) {
                if (a.debtor < b.debtor)
                    return -1;
                if (a.debtor > b.debtor)
                    return 1;
                return 0;
            });
            FileUtil.writeCSVFile(csvOutputFile, debtList);
        } else {
            console.log("CSV file contains no data.");
        }
    });
} else {
    console.log("MISSING CSV file, please provide path for CSV file.");
}
