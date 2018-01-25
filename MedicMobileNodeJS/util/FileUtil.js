"use strict";

var fs = require("fs");
var csv = require("fast-csv");

class FileUtil {
    constructor() {}

    readCSVFile(csvPath, readCallback) {
        var _data = [];
        csv.fromPath(csvPath)
            .on("data", function(data) {
                _data.push(data);
            })
            .on("end", function() {
                readCallback(_data);
            });
    }

    writeCSVFile(csvOutputPath, debtList) {
        var _finalData = [];
        for (var n in debtList) {
            _finalData.push([debtList[n].debtor, debtList[n].creditor, debtList[n].amount])
        }

        var writeStream = fs.createWriteStream(csvOutputPath);
        csv.write(_finalData).pipe(writeStream);
    }
}

module.exports = new FileUtil();
