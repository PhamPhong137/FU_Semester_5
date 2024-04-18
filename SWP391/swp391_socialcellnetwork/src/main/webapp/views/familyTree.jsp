<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="models.Person" %>
<%@ page import="models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Family Tree</title>

        <link rel="stylesheet" href="css/familyTree.css"/>
        <style>.showlist-title  h5{
                top: 5%;
                background-color: white;
                left: 1%;
                border-radius: 10px;
                padding: 15px 15px 10px 15px;
                box-shadow: 0 0 20px #0000006f;
                width: 200px;
                margin-left: 10px;
            }

            .showlist-title{
                position: fixed;
                margin-top: 0px;
                z-index: 999;
            }
            .showlist-title h5{
                margin-top: 40px;
            }</style>
        <script>
            function showSearchResults(results, isViewTree) {
                var resultsDiv = document.getElementById('searchResults');
                resultsDiv.innerHTML = '';

                if (results.length > 0) {
                    var resultsTable = document.createElement('table');
                    resultsTable.className = 'results-table';

                    var thead = document.createElement('thead');
                    thead.innerHTML = '<tr><th>Avatar</th><th>Name</th><th>Phone Number</th><th>Action</th></tr>';
                    resultsTable.appendChild(thead);

                    var tbody = document.createElement('tbody');

                    results.forEach(function (user) {
                        var tr = document.createElement('tr');


                        var tdImage = document.createElement('td');
                        var userImage = document.createElement('img');
                        userImage.src = "img/" + user.image;
                        userImage.className = 'user-image';
                        tdImage.appendChild(userImage);
                        tr.appendChild(tdImage);


                        var tdName = document.createElement('td');
                        tdName.textContent = user.f_name + ' ' + user.l_name;
                        tr.appendChild(tdName);


                        var tdPhone = document.createElement('td');
                        tdPhone.textContent = user.phone_number;
                        tr.append(tdPhone);


                        var tdButton = document.createElement('td');
                        var button = document.createElement('button');
                        if (isViewTree) {
                            button.textContent = 'View Tree';
                            button.onclick = function () {
                                window.location.href = '/SocialCellNetwork/grafttree?targetId=' + user.user_id;
                            };
                        } else {
                            button.textContent = 'Add';
                            button.onclick = function () {
                                fillForm(user);
                            };
                        }
                        tdButton.appendChild(button);
                        tr.appendChild(tdButton);


                        tbody.appendChild(tr);
                    });


                    resultsTable.appendChild(tbody);


                    resultsDiv.appendChild(resultsTable);

                    var modal = document.getElementById('searchModal');
                    modal.style.display = 'block';
                }
            }
        </script>
    </head>
    <body>
        <%@include file="/components/header.jsp"%>
        <div class="container-fluid body-content">
            <form id="hiddenForm" method="POST" action="familytree" style="display: none;">
                <input type="hidden" name="currentUserId" id="hiddenCurrentUserId" value="<%= request.getAttribute("currentUserId") %>">
                <input type="hidden" name="actionType" id="hiddenActionType">
                <input type="hidden" name="name" id="hiddenName">
                <input type="hidden" name="birthDate" id="hiddenBirthDate">
                <input type="hidden" name="deathDate" id="hiddenDeathDate">
                <input type="hidden" name="optionalInfo" id="hiddenOptionalInfo">
                <input type="hidden" name="relation" id="hiddenRelation">
                <input type="hidden" name="targetName" id="hiddenTargetName">
                <input type="hidden" name="targetDob" id="hiddenTargetDob">
                <input type="hidden" name="searchTerm" id="hiddenSearchTerm">
                <input type="hidden" id="hiddenNodeId" name="nodeId">
                <input type="hidden" id="hiddenUserIdGet" name="userIdGet">
                <input type="hidden" id="hiddenFormActive" name="formActive">
                <!-- Thêm các trường khác tương tự nếu cần -->
            </form>
            <% if (session.getAttribute("message") != null) { %>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    showMessage('<%= session.getAttribute("message") %>');
                });
            </script>
            <% session.removeAttribute("message"); } %>
            <c:if test="${requestScope.stateOfScreen == 'new' && requestScope.role == 'edit'}">
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        showMessage('You need to create a tree first!');
                        document.getElementById("hiddenFormActive").value = "create";
                        showSpecificForm('formCreate');
                    });
                </script>
            </c:if>
            <c:if test="${requestScope.stateOfScreen == 'new' && requestScope.role != 'edit'}">
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        showMessage('You need to ask your family leader to create a tree first!');
                    });
                </script>
            </c:if>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
            <div id="setting-icon" class="settings-icon" style="position: fixed; right: 20px; bottom: 20px; cursor: pointer;">
                <i class="fas fa-cog" style="font-size: 24px;"></i>
            </div>

            <div id="settings-menu" style="display: none; position: fixed; right: 20px; bottom: 60px; background: white; padding: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                <ul>
                    <c:if test="${requestScope.stateOfScreen == 'new' && requestScope.role == 'edit'}">
                        <li><a href="#" id="showCreateForm">Create a new tree</a></li>
                        </c:if>
                        <c:if test="${requestScope.stateOfScreen != 'new' && requestScope.role == 'edit'}">
                        <li><a href="#" id="showAddForm">Add node</a></li>
                        <li><a href="#" id="showUpdateForm">Update node</a></li>
                        <li><a href="#" id="showDeleteForm">Delete node</a></li>
                        <li><a href="#" id="showGraftForm">Graft tree</a></li>
                        </c:if>
                        <c:if test="${requestScope.role != 'edit'}">
                        <li>You cannot edit the family tree</li>
                        </c:if>
                </ul>

            </div>


            <div class="showlist-title">
                <h5>Family Tree <%= request.getAttribute("state") %></h5>
            </div>


            <div class="tree-container" id="treeContainer">
                <!-- Nội dung khung cây ở đây -->
                <div class="tree" id="tree"></div>
            </div>
            <div class="form-container" id="formContainer" style="display:none;">

                <form id="formCreate">
                    <h3>Node to Create root of Tree</h3>
                    <label for="nameCreate">Name:</label>
                    <input type="text" id="nameCreate" name="name" required><br>

                    <label for="dobCreate">Date of birth:</label>
                    <input type="date" id="dobCreate" name="birthDate" required><br>

                    <label for="dodCreate">Date of death (optional):</label>
                    <input type="date" id="dodCreate" name="deathDate"><br>

                    <label for="optionalInfoCreate">Optional information:</label>
                    <input type="text" id="optionalInfoCreate" name="optionalInfo"><br>

                    <h3>Create by search in the connected people list</h3>
                    <label for="searchCreate">Name/Number phone:</label>
                    <input type="text" id="searchCreate" name="searchTerm"><br>

                    <button type="button" onclick="setFormAction('search')">Search</button>
                    <button type="button" onclick="setFormAction('searchAll')">Show all</button>
                    <br>
                    <button type="button" onclick="setFormAction('createAddYourself')">Add Yourself</button>
                    <button type="button" onclick="setFormAction('create')">Create</button>
                    <button type="button" onclick="hideForm()">Close</button>
                </form>

                <form id="formAdd">

                    <h3>Node to Add</h3>
                    <label for="nameCreate">Name:</label>
                    <input type="text" id="nameAdd" name="name" required><br>

                    <label for="dobCreate">Date of birth:</label>
                    <input type="date" id="dobAdd" name="birthDate" required><br>

                    <label for="dodCreate">Date of death (optional):</label>
                    <input type="date" id="dodAdd" name="deathDate"><br>

                    <label for="optionalInfoCreate">Optional information:</label>
                    <input type="text" id="optionalInfoAdd" name="optionalInfo"><br>

                    <h3>Add by search in your connected people list:</h3>
                    <label for="searchCreate">Name/Number phone:</label>
                    <input type="text" id="searchAdd" name="searchTerm"><br>
                    <button type="button" onclick="setFormAction('search')">Search</button>
                    <button type="button" onclick="setFormAction('searchAll')">Show all</button>


                    <label for="addTargetName">You want to add to:(select a node in tree)</label>
                    <input type="text" id="addTargetName" name="targetName" readonly><br>

                    <label for="addTargetDob">Date of Birth:</label>
                    <input type="date" id="addTargetDob" name="targetDob" readonly><br>

                    <label for="relationWithTarget">Relation with this person:</label>
                    <select id="relationWithTarget" name="relation">
                        <option value="spouse">Spouse</option>
                        <option value="child">Child</option>
                    </select><br>

                    <button type="button" onclick="setFormAction('add')">Add</button>
                    <button type="button" onclick="hideForm()">Close</button>
                </form>
                <form id="formUpdate">

                    <h3>Node to Update(select a node in tree)</h3>
                    <label for="nameCreate">Name:</label>
                    <input type="text" id="nameUpdate" name="name" required><br>

                    <label for="dobCreate">Date of birth:</label>
                    <input type="date" id="dobUpdate" name="birthDate" required><br>

                    <label for="dodCreate">Date of death (optional):</label>
                    <input type="date" id="dodUpdate" name="deathDate"><br>

                    <label for="optionalInfoCreate">Optional information:</label>
                    <input type="text" id="optionalInfoUpdate" name="optionalInfo"><br>

                    <input type="hidden" id="updateNodeId" name="nodeId">

                    <h3>Update by search in your connected people list:</h3>
                    <label for="searchCreate">Name/Number phone:</label>
                    <input type="text" id="searchUpdate" name="searchTerm" ><br>
                    <button type="button" onclick="setFormAction('search')">Search</button>
                    <button type="button" onclick="setFormAction('searchAll')">Show all</button>
                    <button type="button" onclick="setFormAction('update')">Update</button>
                    <button type="button" onclick="hideForm()">Close</button>

                </form>
                <form id="formDelete">
                    <h3>Node to delete(select a node in tree)</h3>
                    <label for="nameDelete">Name:</label>
                    <input type="text" id="nameDelete" name="name" readonly><br>

                    <label for="dobDelete">Date of birth:</label>
                    <input type="date" id="dobDelete" name="birthDate" readonly><br>

                    <input type="hidden" id="deleteNodeId" name="nodeId">

                    <button type="button" onclick="setFormAction('delete')">Delete</button>
                    <button type="button" onclick="hideForm()">Close</button>
                </form>

                <form id="formGraft">
                    <h3>Search in your connected people list to graft</h3>
                    <label for="searchGraft">Name/Phone number:</label>
                    <input type="text" id="searchGraft" name="searchTerm" required><br>

                    <button type="button" onclick="setFormAction('graft')">Search</button>
                    <button type="button" onclick="hideForm()">Close</button>
                </form>

            </div>



        </div>
        <!-- Message Modal -->
        <div id="messageModal" class="modal">
            <div class="modal-content">
                <span class="closeMessage">&times;</span>
                <h2>Message</h2>
                <p id="messageContent"></p>
                <div id="modalButtons"></div>
            </div>
        </div>

        <!-- Info Modal -->
        <div id="infoModal" class="modal">
            <div class="modal-content">
                <span class="closeMessage">&times;</span>
                <h2>Infomation</h2>
                <p id="infoContent"></p>
            </div>
        </div>


        <div id="searchModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Search Results</h2>
                <div id="searchResults"></div>
            </div>
        </div>
        <% if (request.getAttribute("searchResultsJson") != null && !request.getAttribute("searchResultsJs  on").toString().isEmpty()) { %>
        <script>
            var searchResults = <%= request.getAttribute("searchResultsJson") %>;
            var searchForGraft = "<%= request.getAttribute("searchForGraft") != null ? request.getAttribute("searchForGraft") : "" %>"; // Get the searchForGraft attribute
            console.log(searchResults);
            console.log(searchForGraft);

            if (searchForGraft && searchForGraft !== "") {
                // Assuming searchForGraft indicates a specific action, like viewing a tree
                // Modify or call a function as needed based on the value
                showSearchResults(searchResults, true);
            } else {

            }
        </script>
        <% } %>
        <script>
            function buildTreeData(person) {
                let treeData = {
                    id: person.id,
                    userId: person.userId ? person.userId : "",
                    name: person.name,
                    img: person.image ? person.image : "",
                    info: person.information ? person.information : "",
                    birthDate: person.birthDate == "null" ? "Unknown" : person.birthDate,
                    deathDate: person.deathDate ? person.deathDate : "",
                    children: []
                };
                if (person.spouse) {
                    treeData.spouse = {
                        id: person.spouse.id,
                        userId: person.spouse.userId ? person.spouse.userId : "",
                        name: person.spouse.name,
                        img: person.spouse.image ? person.spouse.image : "",
                        info: person.spouse.information ? person.spouse.information : "",
                        birthDate: person.spouse.birthDate == "null" ? "Unknown" : person.spouse.birthDate,
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


            window.onload = function () {
            <% String rootPersonJson = (String) request.getAttribute("rootPersonJson"); %>

                var personData = JSON.parse('<%= rootPersonJson != null ? rootPersonJson : "{}" %>');
                displayFamilyTree(personData);

            }


            var nameMap = {};
            var birthMap = {};
            var deathMap = {};
            var userIdMap = {};
            var infoMap = {};
            var nodeMap = {};
            var imgMap = {};
            var currentUserId = document.getElementById('hiddenCurrentUserId').value;

            var foundCurrentUser = false;

            function checkAndMarkCurrentUser(person) {
                if (person.userId == currentUserId) {
                    foundCurrentUser = true;
                }
            }

            function trimName(name) {
                if (name.length > 30) {
                    return name.substring(0, 27) + "...";
                }
                return name;
            }


            function createTreeElement(person) {
                nameMap[person.id] = person.name;
                birthMap[person.id] = person.birthDate;
                userIdMap[person.id] = person.userId;
                infoMap[person.id] = person.info;
                nodeMap[person.id] = person.id;
                deathMap[person.id] = person.deathDate;
                imgMap[person.id] = person.img;

                let personContent = '<strong>' + trimName(person.name) + '</strong> (' + person.birthDate;
                if (person.deathDate) {
                    personContent += ' - ' + person.deathDate;
                }
                personContent += ')';

                let personElem = document.createElement("li");
                checkAndMarkCurrentUser(person);
                if (person.spouse) {
                    checkAndMarkCurrentUser(person.spouse);
                    nodeMap[person.spouse.id] = person.spouse.id;
                    nameMap[person.spouse.id] = person.spouse.name;
                    birthMap[person.spouse.id] = person.spouse.birthDate;
                    userIdMap[person.spouse.id] = person.spouse.userId;
                    deathMap[person.spouse.id] = person.spouse.deathDate;
                    infoMap[person.spouse.id] = person.spouse.info;
                    imgMap[person.spouse.id] = person.spouse.img;
                    let spouseContent = '<strong>' + trimName(person.spouse.name) + '</strong> (' + person.spouse.birthDate;
                    if (person.spouse.deathDate) {
                        spouseContent += ' - ' + person.spouse.deathDate;
                    }
                    spouseContent += ')';

                    let parentWrapper = document.createElement("div");
                    parentWrapper.className = "parent-wrapper";
                    if (person.userId == currentUserId) {
                        parentWrapper.innerHTML = '<a href="#" id ="current-user" onclick="selectNode(' + person.id + ')">' + personContent + '</a> <a href="#" onclick="selectNode(' + person.spouse.id + ')">' + spouseContent + '</a>';
                    } else if (person.spouse.userId == currentUserId) {
                        parentWrapper.innerHTML = '<a href="#" onclick="selectNode(' + person.id + ')">' + personContent + '</a> <a href="#" id ="current-user" onclick="selectNode(' + person.spouse.id + ')">' + spouseContent + '</a>';
                    } else {
                        parentWrapper.innerHTML = '<a href="#" onclick="selectNode(' + person.id + ')">' + personContent + '</a> <a href="#" onclick="selectNode(' + person.spouse.id + ')">' + spouseContent + '</a>';
                    }
                    personElem.appendChild(parentWrapper);
                } else {
                    if (person.userId == currentUserId) {
                        personElem.innerHTML = '<a href="#" id="current-user" onclick="selectNode(' + person.id + ')">' + personContent + '</a>';
                    } else {
                        personElem.innerHTML = '<a href="#" onclick="selectNode(' + person.id + ')">' + personContent + '</a>';
                    }
                }

                if (person.children && person.children.length > 0) {
                    let childrenList = document.createElement("ul");
                    for (let child of person.children) {
                        let childElem = createTreeElement(child);
                        childrenList.appendChild(childElem);
                    }
                    personElem.appendChild(childrenList);
                }
                return personElem;
            }


            function formatDateOnClick(dateString) {

                var parts = dateString.split('-');

                return parts[0] + '-' + parts[1] + '-' + parts[2];
            }



            function displayFamilyTree(person) {
                var treeContainer = document.getElementById("tree");
                treeContainer.innerHTML = '';

                let treeData = buildTreeData(person);
                console.log(treeData);

                let rootElement = document.createElement("ul");
                let treeElement = createTreeElement(treeData);

                console.log(foundCurrentUser);
                addYourselfButtonIfNotFound();

                rootElement.appendChild(treeElement);
                treeContainer.appendChild(rootElement);

            }
            console.log(nameMap);
            // Wait for the DOM to be fully loaded before manipulating it
            document.addEventListener('DOMContentLoaded', function () {
                var modal = document.getElementById('searchModal');


                var span = document.getElementsByClassName('close')[0];


                span.onclick = function () {
                    modal.style.display = 'none';
                }


                window.onclick = function (event) {
                    if (event.target == modal) {
                        modal.style.display = 'none';
                    }
                }


                var messageModal = document.getElementById('messageModal');
                var closeMessage = document.getElementsByClassName('closeMessage')[0];


                window.showMessage = function (message) {
                    document.getElementById('messageContent').textContent = message;
                    messageModal.style.display = 'block';
                }


                closeMessage.onclick = function () {
                    messageModal.style.display = 'none';
                }


                window.onclick = function (event) {
                    if (event.target == messageModal) {
                        messageModal.style.display = 'none';
                    }
                }
            });





            function fillForm(user) {

                var activeForm = document.querySelector(".form-container form:not([style*='display: none'])");
                if (activeForm) {

                    var nameField = activeForm.querySelector("[name='name']");
                    var birthDateField = activeForm.querySelector("[name='birthDate']");
                    var deathDateField = activeForm.querySelector("[name='deathDate']");
                    var optionalInfoField = activeForm.querySelector("[name='optionalInfo']");

                    if (nameField)
                        nameField.value = user.f_name + ' ' + user.l_name;
                    if (birthDateField)
                        birthDateField.value = formatDate(user.date_birth);
                    if (deathDateField)
                        deathDateField.value = user.date_death ? formatDate(user.date_death) : '';
                    if (optionalInfoField)
                        optionalInfoField.value = "";
                }


                document.getElementById('hiddenName').value = user.f_name + ' ' + user.l_name;
                document.getElementById('hiddenBirthDate').value = formatDate(user.date_birth);
                document.getElementById('hiddenDeathDate').value = user.date_death ? formatDate(user.date_death) : '';
                document.getElementById('hiddenOptionalInfo').value = " ";
                document.getElementById('hiddenUserIdGet').value = user.user_id;


                document.getElementById('searchModal').style.display = 'none';
            }



            function formatDate(dateString) {
                var dateParts = dateString.match(/(\d+), (\d+)/);
                var date = new Date(dateString);
                var year = date.getFullYear();
                var month = ('0' + (date.getMonth() + 1)).slice(-2);
                var day = ('0' + dateParts[1]).slice(-2);
                return year + '-' + month + '-' + day;
            }

            function setFormAction(action) {


                document.getElementById('hiddenActionType').value = action;


                var activeForm = document.querySelector(".form-container form:not([style*='display: none'])");


                var fieldsToUpdate = [
                    {formField: 'name', hiddenField: 'hiddenName'},
                    {formField: 'birthDate', hiddenField: 'hiddenBirthDate'},
                    {formField: 'deathDate', hiddenField: 'hiddenDeathDate'},
                    {formField: 'optionalInfo', hiddenField: 'hiddenOptionalInfo'},
                    {formField: 'relation', hiddenField: 'hiddenRelation'},
                    {formField: 'targetName', hiddenField: 'hiddenTargetName'},
                    {formField: 'targetDob', hiddenField: 'hiddenTargetDob'},
                    {formField: 'searchTerm', hiddenField: 'hiddenSearchTerm'}
                ];

                fieldsToUpdate.forEach(function (field) {
                    var formElement = activeForm.querySelector(`[name='` + field.formField + `']`);
                    if (formElement) {
                        document.getElementById(field.hiddenField).value = formElement.value;
                    } else {
                        document.getElementById(field.hiddenField).value = '';
                    }
                });

                if (!validateFormInputs(action)) {
                    return;
                }
                if (action == "update")
                    confirmUpdate();
                else if (action == "delete")
                    confirmDelete();
                else if (action == "search")
                    performSearch();
                else if (action == "searchAll")
                    performSearchAll();
                else if (action == "graft")
                    performGraftSearch();
                else
                    document.getElementById('hiddenForm').submit();
            }





            function showMessage(message) {
                var messageModal = document.getElementById('messageModal');
                var messageContent = document.getElementById('messageContent');

                messageContent.textContent = message;
                messageModal.style.display = 'block';


                document.getElementsByClassName('closeMessage')[0].onclick = function () {
                    messageModal.style.display = 'none';
                }


                window.onclick = function (event) {
                    if (event.target == messageModal) {
                        messageModal.style.display = 'none';
                    }
                }
            }

            function showInfoModal(name, birthDate, deathDate, optionalInfo, userId) {
                var infoModal = document.getElementById('infoModal');
                var infoContent = document.getElementById('infoContent');


                var content = "<strong>Name:</strong> ";

                if (userId && userId != "-1") {
                    content += "<a href='profile?user_id=" + userId + "'>" + name + "</a>";
                } else {
                    content += name;
                }
                content += "<br>" +
                        "<strong>Birth Date:</strong> " + birthDate + "<br>" +
                        "<strong>Death Date:</strong> " + (deathDate || "N/A") + "<br>" +
                        "<strong>Optional Info:</strong> " + (optionalInfo || "N/A");

                infoContent.innerHTML = content;
                infoModal.style.display = 'block';


                document.getElementsByClassName('closeMessage')[0].onclick = function () {
                    infoModal.style.display = 'none';
                }


                window.onclick = function (event) {
                    if (event.target == infoModal) {
                        infoModal.style.display = 'none';
                    }
                }
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


                document.getElementById('messageModal').style.display = 'block';


                window.onclick = function (event) {
                    var modal = document.getElementById('messageModal');
                    if (event.target == modal) {
                        event.stopPropagation();
                    }
                };
            }

            function confirmUpdate() {
                showConfirmModal('Are you want to update this node?', function () {
                    document.getElementById('hiddenForm').submit(); // Submit the form
                }, true);
            }

            function confirmDelete() {
                showConfirmModal('Are you want to delete this node?', function () {
                    document.getElementById('hiddenForm').submit(); // Submit the form
                }, true);
            }
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

            function showForm() {
                document.body.classList.add('show-form');
            }

            function hideForm() {
                document.body.classList.remove('show-form');
            }

            function showSpecificForm(formId) {

                var formContainer = document.getElementById('formContainer');
                formContainer.style.display = 'block';


                document.querySelectorAll('.form-container form').forEach(function (form) {
                    form.style.display = 'none';
                });


                var specificForm = document.getElementById(formId);
                if (specificForm) {
                    specificForm.style.display = 'block';
                }

                document.getElementById('settings-menu').style.display = 'none';


                document.body.classList.add('show-form');
            }



            function hideForm() {
                document.body.classList.remove('show-form');
                document.querySelectorAll('.form-container form').forEach(function (form) {
                    document.getElementById("hiddenFormActive").value = "";
                    form.style.display = 'none';
                });

                document.getElementById('settings-menu').style.display = 'none';
            }
            document.addEventListener('DOMContentLoaded', function () {
                if (document.getElementById('showCreateForm')) {
                    document.getElementById('showCreateForm').addEventListener('click', function () {
                        document.getElementById("hiddenFormActive").value = "create";
                        showSpecificForm('formCreate');
                    });
                }

                if (document.getElementById('showAddForm')) {
                    document.getElementById('showAddForm').addEventListener('click', function () {
                        document.getElementById("hiddenFormActive").value = "add";
                        showSpecificForm('formAdd');
                    });
                }

                if (document.getElementById('showUpdateForm')) {
                    document.getElementById('showUpdateForm').addEventListener('click', function () {
                        document.getElementById("hiddenFormActive").value = "update";
                        showSpecificForm('formUpdate');
                    });
                }

                if (document.getElementById('showDeleteForm')) {
                    document.getElementById('showDeleteForm').addEventListener('click', function () {
                        document.getElementById("hiddenFormActive").value = "";
                        showSpecificForm('formDelete');
                    });
                }
                if (document.getElementById('showGraftForm')) {
                    document.getElementById('showGraftForm').addEventListener('click', function () {
                        document.getElementById("hiddenFormActive").value = "";
                        showSpecificForm('formGraft');
                    });
                }
            });

            function enableDragScroll(id) {
                const ele = document.getElementById(id);
                let pos = {top: 0, left: 0, x: 0, y: 0};

                const mouseDownHandler = function (e) {
                    pos = {
                        left: ele.scrollLeft,
                        top: ele.scrollTop,
                        x: e.clientX,
                        y: e.clientY
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
                enableDragScroll('treeContainer');
                enableDragScroll('tree');
            });





        </script>
        <script>
            function selectNode(personId) {
                var name = nameMap[personId];
                var birth = birthMap[personId];
                var death = deathMap[personId] || "";
                var userId = userIdMap[personId];
                var info = infoMap[personId];
                var img = imgMap[personId];


                var formattedBirth = formatDateOnClick(birth);
                console.log(formattedBirth);

                if (name && formattedBirth) {

                    var activeForm = document.querySelector(".form-container form:not([style*='display: none'])");
                    console.log(activeForm);
                    if (!activeForm) {
                        showInfoModal(name, birth, death, info, userId);
                    } else {

                        var nodeIdField = activeForm.querySelector("[name='nodeId']");
                        var targetPersonField = activeForm.querySelector("[name='targetName']");
                        var birthTargetDateField = activeForm.querySelector("[name='targetDob']");
                        var nameField = activeForm.querySelector("[name='name']");
                        var birthField = activeForm.querySelector("[name='birthDate']");
                        var deathField = activeForm.querySelector("[name='deathDate']");
                        var infoField = activeForm.querySelector("[name='optionalInfo']");

                        if (nodeIdField)
                            nodeIdField.value = personId;
                        if (targetPersonField)
                            targetPersonField.value = name;
                        if (birthTargetDateField)
                            birthTargetDateField.value = formattedBirth;
                        if (!targetPersonField) {
                            nameField.value = name;
                            birthField.value = formattedBirth;
                            if (deathField)
                                deathField.value = death;
                            if (infoField)
                                infoField.value = info;
                        }
                    }


                    document.getElementById('hiddenNodeId').value = personId;
                    document.getElementById('hiddenTargetName').value = name;
                    document.getElementById('hiddenTargetDob').value = formattedBirth;
                    document.getElementById('hiddenUserIdGet').value = userId;
                } else {
                    console.error('Person not found for id:', personId);

                }
            }

            function performSearch() {
                var searchTerm = document.getElementById('hiddenSearchTerm').value; // Lấy giá trị từ input tìm kiếm
                fetch('/SocialCellNetwork/searchoftree', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'searchTerm=' + encodeURIComponent(searchTerm)
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.length === 0) {
                                showMessage("Dont find any connected person match the name/phone number!");
                            } else {
                                showSearchResults(data, false); // Cập nhật UI với kết quả tìm kiếm
                            }
                        })
                        .catch(error => console.error('Error:', error));
            }

            function performGraftSearch() {
                var searchTerm = document.getElementById('hiddenSearchTerm').value; // Lấy giá trị từ input tìm kiếm
                fetch('/SocialCellNetwork/searchoftree', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'searchTerm=' + encodeURIComponent(searchTerm)
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.length === 0) {
                                showMessage("Dont find any connected person match the name/phone number!");
                            } else {
                                showSearchResults(data, true); // Cập nhật UI với kết quả tìm kiếm
                            }
                        })
                        .catch(error => console.error('Error:', error));
            }
            function performSearchAll() {
                var searchTerm = document.getElementById('hiddenSearchTerm').value; // Lấy giá trị từ input tìm kiếm
                fetch('/SocialCellNetwork/searchoftree', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'all=' + encodeURIComponent("True")
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Network response was not ok');
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.length === 0) {
                                showMessage("Dont find any connected person match the name/phone number!");
                            } else {
                                showSearchResults(data, false); // Cập nhật UI với kết quả tìm kiếm
                            }
                        })
                        .catch(error => console.error('Error:', error));
            }

            function validateFormInputs(action) {
                var isValid = true;
                var missingFields = [];



                if (action === "add") {
                    var specialFields = ['name', 'birthDate', 'relation', 'targetName'];
                    specialFields.forEach(field => {
                        var inputElement = document.querySelector("[name='" + field + "']");
                        if ((field === 'name' || field === 'birthDate') && (!inputElement || inputElement.value.trim() === "")) {
                            isValid = false;
                            missingFields.push(field.charAt(0).toUpperCase() + field.slice(1));
                            if (inputElement)
                                inputElement.classList.add('input-error');
                        } else {
                            if (inputElement)
                                inputElement.classList.remove('input-error');
                        }
                        if (field === 'relation' && (!inputElement || inputElement.value === "none")) {
                            isValid = false;
                            missingFields.push("Relation Type");
                            if (inputElement)
                                inputElement.classList.add('input-error');
                        } else {
                            if (inputElement)
                                inputElement.classList.remove('input-error');
                        }

                        if (field === 'targetName' && (!inputElement || !inputElement.value)) {
                            isValid = false;
                            missingFields.push("Target Person");
                            if (inputElement)
                                inputElement.classList.add('input-error');
                        } else {
                            if (inputElement)
                                inputElement.classList.remove('input-error');
                        }
                    });
                }


                if (action === "search" || action === "graft") {
                    var searchTermInput = document.querySelector("[name='searchTerm']");
                    console.log(searchTermInput);
                    if (!searchTermInput || !searchTermInput.value) {
                        isValid = false;
                        missingFields.push("Search Term");
                        if (searchTermInput)
                            searchTermInput.classList.add('input-error');
                    } else {
                        if (searchTermInput)
                            searchTermInput.classList.remove('input-error');
                    }
                }


                if (action === "update") {
                    var requiredFieldsUpdate = ['name', 'birthDate', 'nodeId'];
                    requiredFieldsUpdate.forEach(field => {
                        var inputElement = document.querySelector("#formUpdate [name='" + field + "']");
                        if (!inputElement || !inputElement.value) {
                            isValid = false;
                            if (field === 'nodeId') {

                                missingFields.push("You did not select a node for update");
                            } else {

                                missingFields.push(field.charAt(0).toUpperCase() + field.slice(1));
                            }
                            if (inputElement)
                                inputElement.classList.add('input-error');
                        } else {
                            if (inputElement)
                                inputElement.classList.remove('input-error');
                        }
                    });
                }



                if (action === "delete") {
                    var nodeIdDelete = document.querySelector("#formDelete [name='nodeId']");
                    if (!nodeIdDelete || !nodeIdDelete.value) {
                        isValid = false;
                        missingFields.push("You did not select a node");
                        nodeIdDelete.classList.add('input-error');
                    } else {
                        nodeIdDelete.classList.remove('input-error');
                    }
                }

                if (!isValid) {
                    showMessage("Missing or invalid inputs : " + missingFields.join(", "));
                }

                return isValid;
            }



            function addYourselfButtonIfNotFound() {
                if (!foundCurrentUser) {

                    const formActions = {
                        "formAdd": "addAddYourself",
                        "formUpdate": "updateAddYourself"
                    };

                    Object.keys(formActions).forEach(formId => {
                        const actionType = formActions[formId];
                        const form = document.getElementById(formId);
                        if (form) {
                            const buttonHTML = `<button type="button" onclick="setFormAction('` + actionType + `')">Add Yourself</button>`;
                            form.insertAdjacentHTML('beforeend', buttonHTML);
                        }
                    });
                }
            }

        </script>

    </body>
    <script src="script.js"></script>
</html>
