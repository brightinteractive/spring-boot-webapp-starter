import $ from 'jquery';
import codePrint from './lib/codeprint.js';

$(function() {
    // Make sure this is only used on styleguide page
    if ($('.style-guide').length > 0 ) {
        codePrint.init({
            usePrism: true
        });
    }
});
