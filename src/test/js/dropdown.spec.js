'use strict';

/* global loadFixtures */

import $ from 'jquery';
import 'jasmine-jquery';
import setFixturesPath from './helpers/fixtures-path';
import dropdown from '../../main/js/dropdown';

setFixturesPath();

describe('dropdown', function(){

    var $dropdownTarget;

    beforeEach(function () {
        loadFixtures('dropdown.html');

        var dropdownOptions = {
            trigger: $('.js-toggle-dropdown'),
            dropdownTarget: $('.js-dropdown')
        };

        $dropdownTarget = $('.js-dropdown-target');

        $('.js-custom').data(dropdownOptions);

        dropdown.init();

    });

    describe('using dropdown', function(){

        it('on click show the dropdown', function(){

            $('.js-toggle-dropdown').trigger('click');
            expect($dropdownTarget).toHaveCss({'display': 'block'});

        });

        it('on click hide the open dropdown', function(){

            $('.js-toggle-dropdown').trigger('click');
            $('.js-toggle-dropdown').trigger('click');
            expect($dropdownTarget).toHaveCss({'display': 'none'});

        });

        it('on click of item outisde the dropdown hide the dropdown', function(){

            $('.js-toggle-dropdown').trigger('click');
            $('body').trigger('click');
            expect($dropdownTarget).toHaveCss({'display': 'none'});

        });

    });

});

describe('multiple dropdowns', function(){

    beforeEach(function () {
        loadFixtures('dropdown.html');
        dropdown.init();
    });

    describe('using multiple dropdowns', function(){

        it('clicking a dropdown should close all other open dropdowns', function(){
            // lets open a dropdown
            $('.js-toggle-dropdown').attr('data-dropdown-active',' true');
            $('.js-dropdown-target').attr('data-dropdown-is-open',' true');

            $('.js-toggle-dropdown-custom').trigger('click');

            expect($('.js-dropdown-target').attr('data-dropdown-is-open')).toBe('false');

        });

    });

});
