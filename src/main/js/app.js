import $ from 'jquery';
import './polyfills';
import './styleguide';
import dropdown from './dropdown.js';
import responsiveTabs from './tabs.js';
import dateAndTimePicker from'./datetimepicker';

$(function() {
    // Used across entire webapp
    dropdown.init();
    responsiveTabs.init();
    dateAndTimePicker();
});
