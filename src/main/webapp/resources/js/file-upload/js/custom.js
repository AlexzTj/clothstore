$(document).ready(function () {

    // enable fileuploader plugin
    $('#featuredUpload').fileuploader({
        extensions: ['jpg', 'jpeg', 'png'],
        limit: 1,
        thumbnails:{
            item2: '<li class="fileuploader-item">' +
            '<div class="columns">' +
            '<div class="column-thumbnail">${image}</div>' +
            '<div class="column-title">' +
            '<div title="${name}">Featured</div>' +
            '<span>you can choose only one featured image</span>' +
            '</div>' +
            '<div class="column-actions">' +
            '<a class="fileuploader-action fileuploader-action-remove" title="Remove"><i></i></a>' +
            '</div>' +
            '</div>' +
            '<div class="progress-bar2">${progressBar}<span></span></div>' +
            '</li>',
        },
        onRemove: function (item, listEl, parentEl, newInputEl, inputEl) {
            var result =  removeImage(item);
            $.when( result ).done(function(){
                return typeof result === "undefined" ? false : result;
            });

        }
    });
    $('#galleryUpload').fileuploader({
        extensions: ['jpg', 'jpeg', 'png'],
        changeInput: ' ',
        theme: 'thumbnails',
        enableApi: true,
        addMore: true,
        thumbnails: {
            box: '<div class="fileuploader-items">' +
            '<ul class="fileuploader-items-list">' +
            '<li class="fileuploader-thumbnails-input"><div class="fileuploader-thumbnails-input-inner">+</div></li>' +
            '</ul>' +
            '</div>',
            item: '<li class="fileuploader-item">' +
            '<div class="fileuploader-item-inner">' +
            '<div class="thumbnail-holder">${image}</div>' +
            '<div class="actions-holder">' +
            '<a class="fileuploader-action fileuploader-action-remove" title="Remove"><i class="remove"></i></a>' +
            '</div>' +
            '<div class="progress-holder">${progressBar}</div>' +
            '</div>' +
            '</li>',
            item2: '<li class="fileuploader-item">' +
            '<div class="fileuploader-item-inner">' +
            '<div class="thumbnail-holder">${image}</div>' +
            '<div class="actions-holder">' +
            '<a class="fileuploader-action fileuploader-action-remove" title="Remove"><i class="remove"></i></a>' +
            '</div>' +
            '</div>' +
            '</li>',
            startImageRenderer: true,
            canvasImage: false,
            _selectors: {
                list: '.fileuploader-items-list',
                item: '.fileuploader-item',
                start: '.fileuploader-action-start',
                retry: '.fileuploader-action-retry',
                remove: '.fileuploader-action-remove'
            },
            onItemShow: function (item, listEl) {
                var plusInput = listEl.find('.fileuploader-thumbnails-input');

                plusInput.insertAfter(item.html);

                if (item.format == 'image') {
                    item.html.find('.fileuploader-item-icon').hide();
                }
            }
        },
        afterRender: function (listEl, parentEl, newInputEl, inputEl) {
            var plusInput = listEl.find('.fileuploader-thumbnails-input'),
                api = $.fileuploader.getInstance(inputEl.get(0));

            plusInput.on('click', function () {
                api.open();
            });
        },
        onRemove: function (item, listEl, parentEl, newInputEl, inputEl) {
            var result =  removeImage(item);
            return typeof result === "undefined" ? false : result;
        }

    });

    function removeImage(item) {
        if (item.choosed) return true;

        $.ajax({
            url: "/admin/deleteImage/" + item.name,
            headers: {
                [_csrf_header] : _csrf_token
            },
            method: 'POST',
            success: function(data){
                return true;
            },
            error:function (data) {
                return false;
            }
        });

    }

});