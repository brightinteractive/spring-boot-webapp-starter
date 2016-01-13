/*
 ----------------------------------------------------------------------
 Dropdown
 Shows / hides dropdown menus based on clicks.
 ----------------------------------------------------------------------
 */

import $ from 'jquery';

var dropdown;

dropdown = (function() {
    'use strict';

    var $allDropdownTriggers,
        $currentTrigger,
        $dropdown,
        $pageBody;

    function init(){
        $pageBody = $('body');
        $allDropdownTriggers = $('[data-dropdown]');

        if ($allDropdownTriggers.length > 0) {
            monitorTriggerElement();
            closeWhenClickedOut();
        }
    }

    function monitorTriggerElement(){
        $allDropdownTriggers.on('click', function(event){

            event.stopPropagation(); // Stop event propagating to the body
            event.preventDefault();

            $currentTrigger = $(this);

            // remove any active properties on other dropdowns
            $allDropdownTriggers.attr('data-dropdown-active', 'false');

            // set this as the new active dropdown
            $currentTrigger.attr('data-dropdown-active', 'true');

            // get the dropdown list associated with the current dropdown
            $dropdown = $('.'+$currentTrigger.attr('data-dropdown'));

            $dropdown.on('click', function(event) {
                // stop clicks on the dropdown body from propagating up to the body.
                event.stopPropagation();
            });

            toggleDropdown($dropdown);
        });
    }

    function closeWhenClickedOut() {
        $pageBody.click(function() {
            closeAllDropdowns();
        });
    }

    function toggleDropdown($dropdown) {
        // check if dropdown is open and perform correct action
        if ($dropdown.attr('data-dropdown-is-open') === 'true') {
            closeDropdown($dropdown);
        } else {
            openDropdown($dropdown);
        }
    }

    function openDropdown($dropdown) {
        closeAllDropdowns();    // close all open dropdowns
        $dropdown.show();
        $dropdown.attr('data-dropdown-is-open', 'true');
    }

    function closeDropdown($dropdown) {
        $dropdown.hide();
        $dropdown.attr('data-dropdown-is-open', 'false');
    }

    function closeAllDropdowns() {
        $('[data-dropdown-active]').each(function() {
            var $thisDropdown = $('.'+$(this).attr('data-dropdown'));
            closeDropdown($thisDropdown);
        });
    }

    return {
        init:init
    };

}());

export default dropdown;

