//  show modal to create new status

document.getElementById('statusInput').onclick = function () {
    $('#popUpStatusCreate').modal('show');
};

function showStatusEdit(id) {
   $(`#popUpStatusEdit` + id).modal('show');
}


function showSaveForm(id) {
     $(`#formSaveStatus` + id).modal('show');
    
}
function showConfirmDelete(id) {
    $(`#popUpToConfirmDelete` + id).modal('show');
}

function toggleDropdown(id) {
    // console.log('toogle');
    var dropdown = document.getElementById("myDropdown" + id);
    // console.log(dropdown.className);
    dropdown.classList.toggle("show");
}

function  toggleDropdownComment(id) {
    var dropdown = document.getElementById("dropdownComment" + id);
    dropdown.classList.toggle("show");
}

window.onclick = function (event) {
    if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
};
//reaction
function toggleLike(status_id, user_id) {
    $.ajax({
        url: "/SocialCellNetwork/reactioncontroller",
        type: "get",
        data: {
            action: "send_data",
            statusId: status_id,
            userId: user_id
        },
        success: function (data) {
            var like = document.getElementById("like" + status_id);
            like.innerHTML = data;
            $.ajax({
                url: "/SocialCellNetwork/reactioncontroller",
                type: "get",
                data: {action: "receive_data",
                    statusId: status_id
                },
                success: function (response) {
                    var countLike = document.getElementById("countLike" + status_id);
                    countLike.innerHTML = response + " <i class='fas fa-heart' style='color: red;'></i>";
                    //       $("#countLike"+status_id).text(response); 
                },
                error: function () {
                    console.log("error at receive");
                }
            });
        },
        error: function () {
            console.log("error");
        }
    });
}

// comment
function showComment(status_id, user_id) {
    if ($(`#comment` + status_id).css("display") === "none") {
        $(`#comment` + status_id).css("display", "block");
        $.ajax({
            url: "/SocialCellNetwork/comment",
            type: "post",
            data: {
                statusId: status_id,
                userId: user_id
            },
            success: function (data) {
                var commentList = document.getElementById("commentList" + status_id);
                commentList.innerHTML += data;
            },
            error: function () {
                console.log("error");
            }
        });
    } else {
        $(`#comment` + status_id).css("display", "none");
        document.getElementById("commentList" + status_id).innerHTML = "";
    }
}

function addNewComment(status_id, user_id) {
//                event.preventDefault();
    contentComment = document.getElementById("contentComment" + status_id);
    if (contentComment.value !== "") {
        $.ajax({
            url: "/SocialCellNetwork/comment",
            type: "get",
            data: {
                action: "send_data",
                statusId: status_id,
                userId: user_id,
                description: contentComment.value
            },
            success: function (data) {
                var commentList = document.getElementById("commentList" + status_id);
                commentList.innerHTML += data;
                contentComment.value = "";
                $.ajax({
                    url: "/SocialCellNetwork/comment",
                    type: "get",
                    data: {action: "receive_data",
                        statusId: status_id
                    },
                    success: function (response) {
                        var countComment = document.getElementById("countComment" + status_id);
                        countComment.innerHTML = response + " Comments";
                    },
                    error: function () {
                        console.log("error at receive");
                    }
                });
            },
            error: function () {
                console.log("error");
            }
        });
    }
}

function editComment(comment_id) {
    var commentContentElement = document.getElementById("commentItem-content" + comment_id);
    var editArea = document.createElement("textarea");
    editArea.value = commentContentElement.innerText;

    commentContentElement.innerHTML = "";
    commentContentElement.appendChild(editArea);
    editArea.focus();
    editArea.setSelectionRange(editArea.value.length, editArea.value.length);
    editArea.style.width = "475px";
    var submitUpdate = document.createElement("button");
    submitUpdate.className = "btn btn-primary"
    submitUpdate.innerHTML = "Update";
    submitUpdate.type = "button";
    commentContentElement.append(submitUpdate);
    submitUpdate.onclick = function () {
        saveComment(comment_id, editArea.value);
        editArea.remove();
        submitUpdate.remove();
    };
}

function saveComment(comment_id, description) {
    console.log(comment_id);
    $.ajax({
        url: "/SocialCellNetwork/editcomment",
        type: "get",
        data: {
            commentId: comment_id,
            description: description
        },
        success: function (data) {
            var commentContentElement = document.getElementById("commentItem-content" + comment_id);
            commentContentElement.innerHTML = "<pre id='commentItem-content'" + comment_id + ">" + description + "</pre>";
        },
        error: function () {
            console.log("error at savecomment");
        }
    });
}

function showConfirmDeleteComment(id) {
    $(`#popUpToConfirmDeleteComment` + id).modal('show');
}

function deleteComment(status_id, comment_id) {
    $.ajax({
        url: "/SocialCellNetwork/editcomment",
        type: "post",
        data: {
            action: "send_data",
            commentId: comment_id
        },
        success: function (data) {
            $(`#commentItem` + comment_id).remove();
            $.ajax({
                url: "/SocialCellNetwork/editcomment",
                type: "post",
                data: {
                    action: "receive_data",
                    statusId: status_id
                },
                success: function (response) {
                    var countComment = document.getElementById("countComment" + status_id);
                    countComment.innerHTML = response + " Comments";
                },
                error: function () {
                    console.log("error at receive");
                }
            });

        },
        error: function () {
            console.log("error at savecomment");
        }
    });
}

//view mode
function showEditAudience(id) {
    $(`#popUpEditAudience` + id).modal('show');
}

function editAudience(id) {
    var radios = document.getElementsByName('editAudience' + id);
    var audienceEditor;
    for (var i = 0; i < radios.length; i++) {
        if (radios[i].checked) {
            console.log(radios[i].value);
            audienceEditor = radios[i].value;
        }
    }
    $.ajax({
        url: "/SocialCellNetwork/statuscontroller",
        type: "get",
        data: {
            id: id,
            access: audienceEditor,
            type: 2
        },
        success: function (data) {
        },
        error: function () {
            console.log("error at savecomment");
        }
    });
}
var count = 1;
window.addEventListener('scroll', function () {
    console.log(document.body.offsetHeight +" "+(window.innerHeight + window.scrollY) );
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        console.log('Cuộn đến cuối trang!');
        $.ajax({
            url: "/SocialCellNetwork/loadmorestatus",
            type: "get",
            data: {
                count: count + 1
            },
            success: function (data) {
                console.log(count);
                count++;
                document.getElementById("AllStatus").innerHTML += data;
            },
            error: function () {
                console.log("error at load more status");
            }
        });
    }
});






