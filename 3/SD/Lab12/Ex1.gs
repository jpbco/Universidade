function sendEmails2() {
  var sheet = SpreadsheetApp.getActiveSheet();
  var dataRange = sheet.getRange("A2:A100");
  var data = dataRange.getValues();

  for (var i = 0; i < data.length; ++i) {
    var row = data[i];
    if (row[0] == '')
      break;

    var subject = 'Última Aula prática SD';
    var message = 'Mensagem de teste aula prática SD'

    MailApp.sendEmail(row[0], subject, message);
    SpreadsheetApp.flush();

  }
}