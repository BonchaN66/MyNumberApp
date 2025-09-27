import '../styles/Footer.css'

export default function Footer() {
  return (
    <footer className="footer">
      <ul className="social-links">
        <li><a href="https://twitter.com/BonchaN_val" target="_blank" rel="noopener noreferrer"><i className="fab fa-x-twitter"></i>Twitter</a></li>
        <li><a href="https://note.com/bonchan_create/n/n98023562840a" target="_blank" rel="noopener noreferrer"><i className="fas fa-sticky-note"></i>Note</a></li>
        <li><a href="https://crowdworks.jp/public/employees/6373558?ref=login_header" target="_blank" rel="noopener noreferrer"><i className="fas fa-briefcase"></i>CrowdWorks</a></li>
        <li><a href="https://github.com/BonchaN66?tab=repositories" target="_blank" rel="noopener noreferrer"><i className="fab fa-github"></i>GitHub</a></li>
      </ul>
      <p className="copyright">
        &copy; 2025 takuya fujisaki. All rights reserved.
      </p>
    </footer>
  );
}