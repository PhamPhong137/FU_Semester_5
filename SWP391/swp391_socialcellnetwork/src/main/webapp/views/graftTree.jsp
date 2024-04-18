<%-- 
    Document   : graftTree
    Created on : Mar 5, 2024, 2:54:56 AM
    Author     : thanh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/graftTree.css"/>
        <title>SCN | Tree</title>
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.jpg">
        <script>
            function buildTreeData(person) {
                let treeData = {
                    id: person.id,
                    userId: person.userId ? person.userId : "",
                    name: person.name,
                    info: person.information ? person.information : "",
                    birthDate: person.birthDate,
                    deathDate: person.deathDate ? person.deathDate : "",
                    children: []
                };
                if (person.spouse) {
                    treeData.spouse = {
                        id: person.spouse.id,
                        userId: person.spouse.userId ? person.spouse.userId : "",
                        name: person.spouse.name,
                        info: person.spouse.information ? person.spouse.information : "",
                        birthDate: person.spouse.birthDate,
                        deathDate: person.spouse.deathDate ? person.spouse.deathDate : ""
                    };
                }

                if (person.children && person.children.length > 0) {
                    for (let i = 0; i < person.children.length; i++) {
                        treeData.children.push(buildTreeData(person.children[i]));
                    }
                }
                return treeData;
            }

            function createTreeElement(person, treeType) {
                let personContent = '<strong>' + person.name + '</strong> (' + person.birthDate;
                if (person.deathDate) {
                    personContent += ' - ' + person.deathDate;
                }
                personContent += ')';

                let personElem = document.createElement("li");

                if (person.spouse) {
                    let spouseContent = '<strong>' + person.spouse.name + '</strong> (' + person.spouse.birthDate;
                    if (person.spouse.deathDate) {
                        spouseContent += ' - ' + person.spouse.deathDate;
                    }
                    spouseContent += ')';

                    let parentWrapper = document.createElement("div");
                    parentWrapper.className = "parent-wrapper";
                    parentWrapper.innerHTML = '<a href="#" onclick="confirmAdd(' + person.id + ', \'' + treeType + '\')">' + personContent + '</a> <a href="#" onclick="confirmAdd(' + person.spouse.id + ', \'' + treeType + '\')">' + spouseContent + '</a>';
                    personElem.appendChild(parentWrapper);
                } else {
                    personElem.innerHTML = '<a href="#" onclick="confirmAdd(' + person.id + ', \'' + treeType + '\')">' + personContent + '</a>';
                }

                if (person.children && person.children.length > 0) {
                    let childrenList = document.createElement("ul");
                    for (let child of person.children) {
                        let childElem = createTreeElement(child, treeType);
                        childrenList.appendChild(childElem);
                    }
                    personElem.appendChild(childrenList);
                }
                return personElem;
            }

            function displayFamilyTree(person, treeContainerId) {
                var treeContainer = document.getElementById(treeContainerId);
                treeContainer.innerHTML = '';
                console.log(treeContainerId);

                let treeData = buildTreeData(person);
                console.log(treeData);

                let rootElement = document.createElement("ul");
                let treeElement = createTreeElement(treeData, treeContainerId);

                rootElement.appendChild(treeElement);
                treeContainer.appendChild(rootElement);
            }

            window.onload = function () {
            <% String myTreeJson = (String) request.getAttribute("myTreeJson"); %>
            <% String targetTreeJson = (String) request.getAttribute("targetTreeJson"); %>

                var myPersonData = JSON.parse('<%= myTreeJson != null ? myTreeJson : "{}" %>');
                var targetPersonData = JSON.parse('<%= targetTreeJson != null ? targetTreeJson : "{}" %>');
                displayFamilyTree(myPersonData, "myTree");
                displayFamilyTree(targetPersonData, "targetTree");

                var actionGraft = document.getElementById("actionGraft").value;
                if (actionGraft === 'true') {
                    showGraftChoiceModal();
                }
            }

            var isGraftChoiceModal = false;
            var isGraftToMyTree = false;

            function showGraftChoiceModal() {
                isGraftChoiceModal = true;
                var message = "The branch you just cut will graft to your tree by choosing a grafting point. "
                        + "Note: The grafting point will replace the root of the branch you just cut!!! Therefore, no coexisting spouse of this graft point or the root of this branch is accepted!!!";

                showConfirmModal(message, null, true);

                var modalButtonsDiv = document.getElementById('modalButtons');
                modalButtonsDiv.innerHTML = '';

                var graftToMyTreeBtn = createGraftOptionButton('Graft to Your Tree', 'graftToMyTree');

                modalButtonsDiv.appendChild(graftToMyTreeBtn);
            }

            function createGraftOptionButton(buttonText, actionValue) {
                var button = document.createElement('button');
                var modal = document.getElementById('messageModal');
                button.textContent = buttonText;
                button.onclick = function () {
                    document.getElementById('actionOfGraft').value = actionValue;
                    if (actionValue === 'graftToMyTree') {
                        isGraftToMyTree = true;
                    }
                    modal.style.display = 'none';
                    isGraftChoiceModal = false;
                };
                return button;
            }


            function showConfirmModal(message, onConfirm, displayConfirmButton = false) {
                document.getElementById('messageContent').textContent = message;
                var modalButtonsDiv = document.getElementById('modalButtons');
                modalButtonsDiv.innerHTML = '';

                if (displayConfirmButton) {
                    var confirmBtn = document.createElement('button');
                    confirmBtn.textContent = 'Yes';
                    confirmBtn.id = 'confirmBtn';
                    confirmBtn.onclick = function () {
                        document.getElementById('messageModal').style.display = 'none';
                        if (typeof onConfirm === 'function') {
                            onConfirm();
                        }
                    };

                    modalButtonsDiv.appendChild(confirmBtn);
                }

                document.getElementById('closeModal').onclick = function () {
                    if (!isGraftChoiceModal) {
                        document.getElementById('messageModal').style.display = 'none';
                    }
                };


                document.getElementById('messageModal').style.display = 'block';

                window.onclick = function (event) {
                    var modal = document.getElementById('messageModal');
                    if (event.target == modal && !isGraftChoiceModal) {
                        modal.style.display = 'none';
                    }
                };
            }

            function confirmAdd(personId, treeType) {
                var actionGraft = document.getElementById('actionGraft').value;
                console.log(actionGraft);
                console.log(treeType);
                if (actionGraft === 'true') {
                    if (treeType === 'myTree') {
                        document.getElementById('nodeIdOfMyTree').value = personId;
                        if (isGraftToMyTree) {
                            showSuccessModal('You have successfully chosen a node from your tree for grafting.', true);
                        } else if (document.getElementById('nodeIdOfTargetTree').value) {
                            showSuccessModal('You have successfully chosen a node from your tree for grafting.', true);
                        } else {
                            showSuccessModal('You have successfully chosen a node from your tree for grafting.', false);
                        }
                    } else if (treeType === 'targetTree') {
                        document.getElementById('nodeIdOfTargetTree').value = personId;
                        if (isGraftToMyTree) {
                            showSuccessModal('You cannot choose a node from the target tree for your graft type!', false);
                        } else if (document.getElementById('nodeIdOfMyTree').value) {
                            showSuccessModal('You have successfully chosen a node from the target tree for grafting.', true);
                        } else {
                            showSuccessModal('You have successfully chosen a node from the target tree for grafting.', false);
                        }
                    }
                } else {
                    if (treeType === 'targetTree') {
                        document.getElementById('nodeIdOfTargetTree').value = personId;
                        showConfirmModal('Are you want to cut the tree to graft in this node?', function () {
                            document.getElementById('chooseGraftNodeForm').submit();
                        }, true);
                    } else {
                        showSuccessModal('You cannot select a node in your tree to cut!');
                    }
                }
            }

            function showSuccessModal(message, isSubmit) {
                // Cập nhật thông điệp
                document.getElementById('messageContent').textContent = message;

                // Ẩn nút nếu không cần
                var modalButtonsDiv = document.getElementById('modalButtons');
                modalButtonsDiv.innerHTML = ''; // Xóa các nút trước đó nếu không cần

                // Hiển thị modal
                document.getElementById('messageModal').style.display = 'block';

                // Đóng modal khi người dùng nhấp vào dấu "X"
                document.getElementById('closeModal').onclick = function () {
                    document.getElementById('messageModal').style.display = 'none';
                    if (isSubmit)
                        document.getElementById('chooseGraftNodeForm').submit();
                };

                // Đóng modal khi người dùng nhấp ra ngoài
                window.onclick = function (event) {
                    if (event.target == document.getElementById('messageModal')) {
                        document.getElementById('messageModal').style.display = 'none';
                        if (isSubmit)
                            document.getElementById('chooseGraftNodeForm').submit();
                    }
                };
            }

            function enableDragScroll(id) {
                const ele = document.getElementById(id);
                let pos = {top: 0, left: 0, x: 0, y: 0};

                const mouseDownHandler = function (e) {
                    pos = {
                        left: ele.scrollLeft,
                        top: ele.scrollTop,
                        x: e.clientX,
                        y: e.clientY,
                    };

                    document.addEventListener('mousemove', mouseMoveHandler);
                    document.addEventListener('mouseup', mouseUpHandler);
                    e.preventDefault(); 
                };

                const mouseMoveHandler = function (e) {
                    const dx = e.clientX - pos.x;
                    const dy = e.clientY - pos.y;
                    ele.scrollTop = pos.top - dy;
                    ele.scrollLeft = pos.left - dx;
                };

                const mouseUpHandler = function () {
                    document.removeEventListener('mousemove', mouseMoveHandler);
                    document.removeEventListener('mouseup', mouseUpHandler);
                };

                ele.addEventListener('mousedown', mouseDownHandler);
            }

            document.addEventListener('DOMContentLoaded', function () {
                enableDragScroll('myTree');
                enableDragScroll('tree-body-right');
                enableDragScroll('tree-body-left');
                enableDragScroll('targetTree');
            });
            document.addEventListener('DOMContentLoaded', function () {
                var settingsIcon = document.getElementById('setting-icon');
                var settingsMenu = document.getElementById('settings-menu');

                settingsIcon.onclick = function () {
                    if (settingsMenu.style.display === 'none') {
                        settingsMenu.style.display = 'block';
                    } else {
                        settingsMenu.style.display = 'none';
                    }
                };

                document.addEventListener('click', function (event) {
                    var isClickInsideElement = settingsIcon.contains(event.target) || settingsMenu.contains(event.target);
                    if (!isClickInsideElement) {
                        settingsMenu.style.display = 'none';
                    }
                });
            });


        </script>
    </head>
    <body>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <div id="setting-icon" class="settings-icon" style="position: fixed; right: 20px; bottom: 20px; cursor: pointer;">
            <i class="fas fa-cog" style="font-size: 24px;"></i>
        </div>

        <div id="settings-menu" style="display: none; position: fixed; right: 20px; bottom: 60px; background: white; padding: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
            <ul>

                <li><a href="/SocialCellNetwork/grafttree?targetId=<%= request.getAttribute("targetId") %>" id="showAddForm">Back to select target node</a></li>
                <li><a href="/SocialCellNetwork/familytree" id="showUpdateForm">Back to home tree</a></li>

            </ul>
        </div>
        <div class="row">
            <div class="col-lg-8 family-tree-body-left" id="tree-body-left">
                <div class="tree-label" id="tree-label">
                    <%= "My Tree: " + ("my".equals(request.getAttribute("stateOfGraft")) ? "(select a node in here)" : "") %>
                </div>
                <div class="tree" id="myTree"></div>
            </div>
            <div class="col-lg-8 family-tree-body-right" id="tree-body-right">
                <div class="tree-label" id="tree-label">
                    <%= "The Target Tree: " + ("target".equals(request.getAttribute("stateOfGraft")) ? "(select a node in here)" : "") %>
                </div>
                <div class="tree" id="targetTree"></div>
            </div>
            <form id="chooseGraftNodeForm" method="POST" action="grafttree">
                <input type="hidden" id="actionGraft" name="actionGraft" value="<%= request.getAttribute("actionGraft") %>" />
                <input type="hidden" id="targetIdInForm" name="targetIdInForm" value="<%= request.getAttribute("targetId") %>" />
                <input type="hidden" id="nodeIdOfTargetTree" name="nodeIdOfTargetTree">
                <input type="hidden" id="nodeIdOfRootTargetTree" name="nodeIdOfRootTargetTree" value="<%= request.getAttribute("nodeIdOfRootTargetTree") %>">
                <input type="hidden" id="nodeIdOfMyTree" name="nodeIdOfMyTree">
                <input type="hidden" id="actionOfGraft" name="actionOfGraft">
            </form>
        </div>

        <!-- Message Modal -->
        <div id="messageModal" class="modal">
            <div class="modal-content">
                <span id="closeModal" class="closeMessage">&times;</span>
                <h2>Message</h2>
                <p id="messageContent"></p>
                <div id="modalButtons"></div>
            </div>
        </div>


    </body>
</html>
