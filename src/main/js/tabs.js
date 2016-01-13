// Responsive Tabs module
// ----------------------------------------------------
// Tabs that become dropdowns at responsive sizes.
// This javascript always fires, but the css classes it applies only
// do anything at mobile device sizes thanks to media queries (see _tabs.scss)
//
// There is also functionality here to check if the tab link starts with a hash,
// if it does it will make the corresponding div visible and hide all sibling
// divs that have the class tabs__content

import $ from 'jquery';

var responsiveTabs;

responsiveTabs = (function() {
    'use strict';

    var $tabs;

    function init() {
        $tabs = $('.tabs');
        handleClicks();
    }

    function handleClicks() {
        $tabs.on('click', 'a', function(event) {
            var $thisLink = $(event.currentTarget),
                $thisLi = $thisLink.parent('li'),
                $thisTabs = $thisLink.closest('.tabs'),
                linkTarget = $thisLink.attr('href');

            if ($thisLi.hasClass('is-active')) {
                toggleTabDropdown($thisTabs);
                // Don't follow the link if the tabs are showing as collapsed as all
                // it needs to do is show the other tabs
                if (areTabsCollapsed($thisLi)) {
                    event.preventDefault();
                }
            } else if (linkTarget.indexOf('#') === 0) {
                switchTabs($thisLi, linkTarget);
                event.preventDefault();
            }

        });

        $tabs.on('click', '.is-active > label', function(event) {
            var $thisTabs = $(event.currentTarget).closest('.tabs');

            toggleTabDropdown($thisTabs);
            event.preventDefault();
        });
    }

    function switchTabs($thisLi, linkTarget) {
        // make correct tab active
        $thisLi.siblings().removeClass('is-active');
        $thisLi.addClass('is-active');

        // show correct div
        $(linkTarget).show().siblings('.tabs__content').hide();
        closeTabHolder($thisLi.parent('.tabs'));
    }

    function toggleTabDropdown($element){
        if ($element.hasClass('is-open')) {
            closeTabHolder($element);
        } else {
            openTabHolder($element);
        }
    }

    function areTabsCollapsed($tab) {
        // Checks to see if the tab has the css property position: relative
        // If it does it means the tabs are in their collapsed state
        return $tab.css('position') === 'relative';
    }

    function openTabHolder($element){
        $element.addClass('is-open');
    }

    function closeTabHolder($element){
        $element.removeClass('is-open');
    }

    return {
        init:init
    };

}());

export default responsiveTabs;

