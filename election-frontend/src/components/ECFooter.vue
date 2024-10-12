<template>
  <div class="page-container">
    <!-- Footer -->
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-logo">
          <img alt="European Commission Logo" src="../assets/logo-ec-footer.png">
          <p>This site is managed by:<br>P203 Team 4</p>
        </div>
        <div class="footer-links">
          <div class="footer-column">
            <a href="/">Home</a>
            <p>Election results</p>
            <ul>
              <li><a href="/uitslagenkaart">Uitslagenkaart</a></li>
              <li><a href="/totaaltabel">Totaaltabel</a></li>
              <li><a href="/zetelsverdeling">Zetelsverdeling</a></li>
              <li><a href="/meerderheidszoeker">Meerderheidszoeker</a></li>
              <li><a href="/minderheidszoeker">Minderheidszoeker</a></li>
            </ul>
            <a href="/forum">Forum</a>
          </div>
          <div class="footer-column">
            <a href="/languages">Languages on our websites</a>
            <a href="/cookies">Cookies</a>
            <a href="/privacy-policy">Privacy policy</a>
            <a href="/legal-notice">Legal notice</a>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  /* No height restriction to allow natural flow of content */
}

.content {
  flex: 1;
  padding: 20px;
  /* Additional padding or styling as needed */
}

.footer {
  background-color: #004494;
  color: white;
  padding: 20px 0;
  font-size: 14px;
  width: 100%; /* Full width for the footer */
}

.footer-container {
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.footer-logo {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: 12px;
}

.footer-logo img {
  margin-bottom: 10px;
}

.footer-links {
  display: flex;
  justify-content: space-between;
  flex: 1;
  padding-left: 20px;
}

.footer-column {
  margin-right: 40px;
}

.footer-column a {
  color: white;
  text-decoration: none;
  font-weight: 400;
  display: block;
  margin-bottom: 10px;
}

.footer-column ul {
  list-style-type: disc;
  padding: 0;
  margin: 10px 0 0;
  padding-left: 20px;
}

.footer-column ul li {
  margin: 5px 0;
}

.footer-column ul li a {
  color: white;
  text-decoration: none;
}

.footer-column a:hover, .footer-column ul li a:hover {
  text-decoration: underline;
}

.footer-column p {
  margin: 0;
  font-weight: 600;
  font-size: 16px;
  color: white;
}
</style>
