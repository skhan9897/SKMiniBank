<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bank.model.Transaction" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Live Transaction Monitor | SK Mini Bank</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
    body { background-color: #f0f4f8; font-family: 'Segoe UI', sans-serif; color: #102a43; }
    .monitor-header { background: linear-gradient(135deg, #102a43 0%, #243b53 100%); color: white; padding: 30px 0; border-radius: 0 0 30px 30px; margin-bottom: 30px; }
    .table-container { background: white; padding: 25px; border-radius: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); }
    .badge-credit { background: rgba(34, 197, 94, 0.1); color: #166534; border: 1px solid #22c55e; }
    .badge-debit { background: rgba(239, 68, 68, 0.1); color: #991b1b; border: 1px solid #ef4444; }
    .badge-status { font-weight: 700; font-size: 10px; text-transform: uppercase; padding: 5px 10px; border-radius: 20px; }
    .refresh-text { font-size: 11px; color: #627d98; font-style: italic; }

    @media print { .no-print { display: none !important; } }
</style>
</head>
<body>

<div class="monitor-header shadow-sm no-print">
    <div class="container-fluid px-5 d-flex justify-content-between align-items-center">
        <div>
            <h2 class="fw-bold mb-0"><i class="fas fa-satellite-dish me-2"></i> Live Transaction Monitor</h2>
            <p class="mb-0 opacity-75 small">Real-time audit of all banking activities across the network</p>
        </div>
        <div class="d-flex align-items-center gap-3">
            <span class="refresh-text">Auto-refreshing in <span id="timer">30</span>s</span>
            <a href="<%=request.getContextPath()%>/DashboardServlet" class="btn btn-outline-light btn-sm rounded-pill px-3"><i class="fa fa-home"></i> Dashboard</a>
            <button onclick="window.location.reload()" class="btn btn-light btn-sm rounded-pill text-primary fw-bold px-4"><i class="fas fa-sync-alt"></i> Refresh Now</button>
        </div>
    </div>
</div>

<div class="container-fluid px-5">

    <div class="row no-print mb-4">
        <div class="col-md-8">
            <div class="input-group shadow-sm">
                <span class="input-group-text border-0 bg-white"><i class="fas fa-search text-muted"></i></span>
                <input type="text" id="txnSearch" class="form-control border-0" placeholder="Filter by Account, Name, Type or Date...">
            </div>
        </div>
        <div class="col-md-4">
            <form action="<%=request.getContextPath()%>/TransactionServlet" method="get" class="d-flex gap-2">
                <input type="text" name="accountNumber" class="form-control border-0 shadow-sm" placeholder="Lookup Account #">
                <button type="submit" class="btn btn-primary px-4 shadow-sm">SEARCH</button>
            </form>
        </div>
    </div>

    <div class="table-container shadow-sm">
        <div class="table-responsive">
            <table class="table table-hover align-middle" id="txnTable">
                <thead class="text-muted small uppercase fw-bold">
                    <tr>
                        <th>ID</th>
                        <th>Account Info</th>
                        <th>Activity Type</th>
                        <th>Amount</th>
                        <th>Settled Balance</th>
                        <th>Transaction Details</th>
                        <th>Timestamp</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <%
                List<Transaction> list = (List<Transaction>)request.getAttribute("transactionList");
                if(list != null && !list.isEmpty()){
                    for(Transaction t : list){
                        String type = t.getTransactionType() != null ? t.getTransactionType().toUpperCase() : "UNKNOWN";
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
                        <div class="small text-muted" style="max-width: 300px;">
                            <i class="fas fa-info-circle me-1 opacity-50"></i> <%= t.getDescription() != null ? t.getDescription() : "Bank Operation" %>
                        </div>
                    </td>
                    <td class="small text-muted"><%= t.getTransactionDate() %></td>
                    <td>
                        <% if("SUCCESS".equalsIgnoreCase(t.getStatus())) { %>
                            <span class="badge bg-success bg-opacity-10 text-success border border-success border-opacity-25 badge-status">Success</span>
                        <% } else { %>
                            <span class="badge bg-danger bg-opacity-10 text-danger border border-danger border-opacity-25 badge-status">Failed</span>
                        <% } %>
                    </td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="8" class="text-center py-5 opacity-50">
                        <i class="fas fa-receipt fa-3x mb-3"></i>
                        <h5>No Transactions Logged</h5>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-4 mb-5 d-flex justify-content-center gap-3 no-print">
        <%
        String accountNumber = (String) request.getAttribute("accountNumber");
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            if (list != null && !list.isEmpty()) {
                accountNumber = list.get(0).getAccountNumber();
            }
        }
        %>
        <a href="<%=request.getContextPath()%>/TransactionPDFServlet?accountNumber=<%=accountNumber%>" class="btn btn-outline-danger shadow-sm px-4">
            <i class="fas fa-file-pdf me-2"></i> EXPORT TO PDF
        </a>
        <button onclick="window.print()" class="btn btn-outline-dark shadow-sm px-4">
            <i class="fas fa-print me-2"></i> PRINT REPORT
        </button>
    </div>
</div>

<script>
    // Live Search
    document.getElementById('txnSearch').addEventListener('keyup', function() {
        let filter = this.value.toLowerCase();
        let rows = document.querySelectorAll('#txnTable tbody tr');
        rows.forEach(row => {
            row.style.display = row.innerText.toLowerCase().includes(filter) ? '' : 'none';
        });
    });

    // Auto-refresh Timer
    let seconds = 30;
    setInterval(() => {
        seconds--;
        document.getElementById('timer').innerText = seconds;
        if (seconds <= 0) window.location.reload();
    }, 1000);
</script>

</body>
</html>
