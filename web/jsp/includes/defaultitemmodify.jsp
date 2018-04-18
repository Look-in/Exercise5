<%--
  Created by IntelliJ IDEA.
  User: shankunassv
  Date: 28.02.2018
  Time: 22:01
  Шаблон для добавления/изменеия Items

--%>

<form name="Modify" action="/pushitemmodify" method="POST">
    <input type="hidden" name="action" value="${ param.action }">
    <input type="hidden" name="itemType" value="${ param.itemType }">
    <table class="table-edit">
        <tr>
            <td>
                Item name:
            </td>
            <td>
                <input type="text" id="uname" name="name"
                       size="30" value="${ item.name }">
            </td>
        </tr>
        <tr>
            <td>
                Price
            </td>
            <td>
                <input type="text" id="uprice" name="price"
                       size="8" value="${ item.price }">
            </td>
        </tr>
        <tr>
            <input type="hidden" name="itemId" value="${ item.itemId }">
            <td>
                Description
            </td>
            <td>
                <textarea name="description" id="description">${ item.description }</textarea>
            </td>
        </tr>
        <tr>
            <td>
                Status:
            </td>
            <td>
                <select name="itemStatus">
                    <c:forEach var="istatus" items="${statuses}">
                        <option value="${istatus.itemStatusId}" ${istatus.itemStatusId == item.itemStatus.itemStatusId ? 'selected="selected"' : ''}>${istatus.itemStatus}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>


