export default function About() {
  return (
    <section className="section reverse" id="about">
      <div className="container section-layout">
        <div className="section-text">
          <h2>About Me</h2>
          <p>元プロゲーマー。現在はWebアプリを個人開発中。</p>
          <p>ネットワーク系のSESを1年経験し、今はバックエンドとフロントエンド知識を取り入れながら、アウトプットを続けています。</p>
          <p>読書が好きで、図書館にまつわる体験や気づきをnoteで発信しています。</p>
          <p>
            ▶︎ <a href="https://note.com/bonchan_create/n/n98023562840a" target="_blank" rel="noopener noreferrer">
              図書館は「雑に使う」と100倍楽しい（note）
            </a>
          </p>
        </div>
        <img src="./img/fps.jpg" alt="FPSの大会風景" className="img-wrapper" />
      </div>
    </section>
  );
}
