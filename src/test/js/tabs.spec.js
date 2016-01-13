'use strict';

/* global loadFixtures */

import $ from 'jquery';
import 'jasmine-jquery';
import setFixturesPath from './helpers/fixtures-path';
import responsiveTabs from '../../main/js/tabs';

setFixturesPath();

describe('tabs', function(){

    var $tabActive,
        $tabHolder,
        $div1,
        $div2;


    beforeEach(function () {
        loadFixtures('tabs.html');

        $tabActive = $('.tabs .is-active a');
        $tabHolder = $('.tabs');
        $div1 = $('#div1');
        $div2 = $('#div2');

        responsiveTabs.init();
    });


    it('Clicking active tab should open tab holder', function(){
        expect($tabHolder).not.toHaveClass('is-open');
        $tabActive.click();
        expect($tabHolder).toHaveClass('is-open');
    });

    it('Clicking active tab on an open tab should collapse open tab holder', function(){
        $tabHolder.addClass('is-open');
        $tabActive.click();
        expect($tabHolder).not.toHaveClass('is-open');
    });

    describe('Clicking a tab link that has a hash target', function() {

        it('Should show the correct div and hide any siblings', function() {
            expect($div1).toBeHidden();
            expect($div2).toBeHidden();

            $('#div1Tab').click();

            expect($div1).toBeVisible();
            expect($div2).toBeHidden();

            $('#div2Tab').click();

            expect($div1).toBeHidden();
            expect($div2).toBeVisible();

        });

        it('Should activate the correct tab', function() {
            var $div1Tab = $('#div1Tab'),
                $div1Li = $div1Tab.parent('li'),
                $div2Tab = $('#div2Tab'),
                $div2Li = $div2Tab.parent('li');

            //check initial state is as expected
            expect($div1Li).not.toHaveClass('is-active');
            expect($div2Li).not.toHaveClass('is-active');

            $div1Tab.click();

            expect($div1Li).toHaveClass('is-active');
            expect($div2Li).not.toHaveClass('is-active');

            $div2Tab.click();

            expect($div1Li).not.toHaveClass('is-active');
            expect($div2Li).toHaveClass('is-active');

        });

    });

});
