import $ from 'jquery';
import 'eonasdan-bootstrap-datetimepicker';

'use strict';

function dateAndTimePicker() {
    // Setup date and time fields
    $('.js-datetimepicker').datetimepicker({
        // this should be consistent with the date format in DateFormatConstants.DATE_FORMAT
        // note that the date format syntaxes here and there are not the same
        format: 'DD/MMM/YYYY HH:mm',
        locale: 'en-gb'
    });

    // Setup date only fields
    $('.js-datepicker').datetimepicker({
        // this should be consistent with the date format in DateFormatConstants.DATE_FORMAT
        // note that the date format syntaxes here and there are not the same
        format: 'DD/MMM/YYYY',
        locale: 'en-gb'
    });
}


export default dateAndTimePicker;
