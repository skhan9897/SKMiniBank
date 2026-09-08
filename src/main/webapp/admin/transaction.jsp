<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bank.model.Transaction" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transaction Manager | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
    body { background-color: #f0f4f8; font-family: 'Segoe UI', sans-serif; color: #102a43; }
    .monitor-header { background: linear-gradient(135deg, #102a43 0%, #243b53 100%); color: white; padding: 30px 0; border-radius: 0 0 30px 30px; margin-bottom: 30px; }
    .table-container { background: white; padding: 25px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); }
    .badge-credit { background: rgba(34, 197, 94, 0.1); color: #166534; border: 1px solid #22c55e; }
    .badge-debit { background: rgba(239, 68, 68, 0.1); color: #991b1b; border: 1px solid #ef4444; }
    .badge-status { font-weight: 700; font-size: 10px; text-transform: uppercase; padding: 5px 10px; border-radius: 20px; }

    .filter-panel { background: rgba(255,255,255,0.8); backdrop-filter: blur(10px); border-radius: 20px; padding: 20px; margin-bottom: 20px; border: 1px solid white; }

    @media print {
        .no-print { display: none !important; }
        body { background: white; }
        .table-container { box-shadow: none; padding: 0; }
        .monitor-header { border-radius: 0; background: #fff !important; color: #000 !important; border-bottom: 2px solid #000; }
        .badge { border: 1px solid #000 !important; color: #000 !important; background: transparent !important; }
    }
</style>
</head>
<body>

<div class="monitor-header shadow-sm no-print">
    <div class="container-fluid px-5 d-flex justify-content-between align-items-center">
        <div>
            <h2 class="fw-bold mb-0"><i class="fas fa-file-invoice-dollar me-2"></i> Statement & Reports</h2>
            <p class="mb-0 opacity-75 small">Generate custom period statements and individual transaction receipts</p>
        </div>
        <div class="d-flex align-items-center gap-3">
            <a href="<%=request.getContextPath()%>/DashboardServlet" class="btn btn-outline-light btn-sm rounded-pill px-3"><i class="fa fa-home"></i> Dashboard</a>
            <button onclick="window.print()" class="btn btn-light btn-sm rounded-pill text-primary fw-bold px-4"><i class="fas fa-print"></i> Print Statement</button>
        </div>
    </div>
</div>

<!-- Print Header (Visible only when printing) -->
<div class="d-none d-print-block text-center mb-4">
    <h2 class="fw-bold">SK MINI BANK</h2>
    <p>Account Transaction Statement</p>
    <hr>
</div>

<div class="container-fluid px-5">

    <div class="filter-panel no-print shadow-sm">
        <form action="<%=request.getContextPath()%>/TransactionServlet" method="get" class="row g-3 align-items-end">
            <div class="col-md-3">
                <label class="form-label small fw-bold">Account Number</label>
                <input type="text" name="accountNumber" class="form-control" value="<%= request.getAttribute("accountNumber") != null ? request.getAttribute("accountNumber") : "" %>" placeholder="Enter Account No." required>
            </div>
            <div class="col-md-2">
                <label class="form-label small fw-bold">From Date</label>
                <input type="date" name="startDate" class="form-control" value="<%= request.getAttribute("startDate") != null ? request.getAttribute("startDate") : "" %>">
            </div>
            <div class="col-md-2">
                <label class="form-label small fw-bold">To Date</label>
                <input type="date" name="endDate" class="form-control" value="<%= request.getAttribute("endDate") != null ? request.getAttribute("endDate") : "" %>">
            </div>
            <div class="col-md-5 d-flex gap-2">
                <button type="submit" class="btn btn-primary px-4"><i class="fas fa-filter me-2"></i>Apply Filter</button>
                <button type="button" onclick="setQuickDate(30)" class="btn btn-outline-secondary btn-sm">Last 30 Days</button>
                <button type="button" onclick="setQuickDate(90)" class="btn btn-outline-secondary btn-sm">Last 3 Months</button>
                <a href="<%=request.getContextPath()%>/TransactionServlet" class="btn btn-light btn-sm"><i class="fas fa-sync"></i> Reset</a>
            </div>
        </form>
    </div>

    <div class="table-container shadow-sm">
        <div class="table-responsive">
            <table class="table table-hover align-middle" id="txnTable">
                <thead class="text-muted small uppercase fw-bold">
                    <tr>
                        <th>ID</th>
                        <th>Account / Name</th>
                        <th>Activity</th>
                        <th>Amount</th>
                        <th>Closing Balance</th>
                        <th>Description</th>
                        <th>Date & Time</th>
                        <th class="no-print">Action</th>
                    </tr>
                </thead>
                <tbody>
                <%
                List<Transaction> list = (List<Transaction>)request.getAttribute("transactionList");
                if(list != null && !list.isEmpty()){
                    for(Transaction t : list){
                        String type = t.getTransactionType() != null ? t.getTransactionType().toUpperCase() : "TXN";
                        boolean isCredit = type.contains("CREDIT") || type.contains("DEPOSIT") || type.contains("LOAN");
                %>
                <tr>
                    <td class="text-muted small">#<%=t.getId()%></td>
                    <td>
                        <div class="fw-bold" style="color: #102a43;"><%=t.getAccountNumber()%></div>
                        <div class="small opacity-50"><%=t.getCustomerName()%></div>
                    </td>
                    <td>
                        <span class="badge badge-status <%= isCredit ? "badge-credit" : "badge-debit" %>">
                            <%=type%>
                        </span>
                    </td>
                    <td class="fw-bold <%= isCredit ? "text-success" : "text-danger" %>">
                        <%= isCredit ? "+" : "-" %> ₹ <%= String.format("%,.2f", t.getAmount()) %>
                    </td>
                    <td class="fw-bold">₹ <%= String.format("%,.2f", t.getBalance()) %></td>
                    <td>
                        <div class="small text-muted" style="max-width: 250px;">
                            <%= t.getDescription() != null ? t.getDescription() : "N/A" %>
                        </div>
                    </td>
                    <td class="small text-muted"><%= t.getTransactionDate() %></td>
                    <td class="no-print">
                        <button onclick="printReceipt('<%=t.getId()%>', '<%=t.getAccountNumber()%>', '<%=t.getCustomerName()%>', '<%=type%>', '<%=t.getAmount()%>', '<%=t.getBalance()%>', '<%=t.getTransactionDate()%>', '<%=t.getDescription()%>')" class="btn btn-sm btn-outline-primary rounded-pill">
                            <i class="fas fa-receipt me-1"></i> Receipt
                        </button>
                    </td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="8" class="text-center py-5 opacity-50">
                        <i class="fas fa-receipt fa-3x mb-3"></i>
                        <h5>No Records Found for this Period</h5>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Receipt Modal Template (Hidden) -->
<div id="receiptTemplate" style="display:none;">
    <div style="width: 350px; padding: 20px; border: 1px solid #eee; font-family: monospace; line-height: 1.6;">
        <div style="text-align: center; border-bottom: 1px dashed #ccc; padding-bottom: 10px; margin-bottom: 10px;">
            <h3 style="margin:0;">SPAY PAYMENT BANK</h3>
            <small>Digital Banking Receipt</small>
        </div>
        <div style="margin-bottom: 10px;">
            <b>TXN ID:</b> <span id="r_id"></span><br>
            <b>DATE:</b> <span id="r_date"></span><br>
        </div>
        <div style="border-bottom: 1px dashed #ccc; padding-bottom: 10px; margin-bottom: 10px;">
            <b>ACCOUNT:</b> <span id="r_acc"></span><br>
            <b>NAME:</b> <span id="r_name"></span><br>
            <b>TYPE:</b> <span id="r_type"></span><br>
        </div>
        <div style="text-align: right; font-size: 18px; margin-bottom: 10px;">
            <b>AMOUNT: ₹ <span id="r_amt"></span></b>
        </div>
        <div style="font-size: 12px; color: #666; margin-bottom: 15px;">
            <b>DESC:</b> <span id="r_desc"></span><br>
            <b>POST BAL: ₹ <span id="r_bal"></span></b>
        </div>
        <div style="text-align: center; font-size: 10px; border-top: 1px dashed #ccc; padding-top: 10px;">
            This is a computer generated receipt.<br>
            Thank you for banking with SPay!
        </div>
    </div>
</div>

<script>
    function setQuickDate(days) {
        let end = new Date();
        let start = new Date();
        start.setDate(start.getDate() - days);

        document.querySelector('input[name="endDate"]').value = end.toISOString().split('T')[0];
        document.querySelector('input[name="startDate"]').value = start.toISOString().split('T')[0];
    }

    function printReceipt(id, acc, name, type, amt, bal, date, desc) {
        document.getElementById('r_id').innerText = id;
        document.getElementById('r_acc').innerText = acc;
        document.getElementById('r_name').innerText = name;
        document.getElementById('r_type').innerText = type;
        document.getElementById('r_amt').innerText = parseFloat(amt).toLocaleString('en-IN', {minimumFractionDigits: 2});
        document.getElementById('r_bal').innerText = parseFloat(bal).toLocaleString('en-IN', {minimumFractionDigits: 2});
        document.getElementById('r_date').innerText = date;
        document.getElementById('r_desc').innerText = desc;

        let content = document.getElementById('receiptTemplate').innerHTML;
        let win = window.open('', '', 'width=400,height=600');
        win.document.write('<html><head><title>Transaction Receipt</title></head><body onload="window.print();window.close()">' + content + '</body></html>');
        win.document.close();
    }
</script>

</body>
</html>
