// -------------------------
//  必要なライブラリ・コンポーネントの読み込み
// -------------------------
import { useEffect } from "react";
import Message from "./components/Message";
import About from "./components/About";
import Hobbies from "./components/Hobbies";
import Skills from "./components/Skills";
import './styles/Section.css';

// -------------------------
//  メインのSectionsコンポーネント
// -------------------------
export default function Sections() {

  // -------------------------
  //  セクションアニメーションのトリガー設定
  // -------------------------
  useEffect(() => {
    // 1) 全ての.section要素を取得
    const sections = document.querySelectorAll(".section");

    // 2) IntersectionObserverで画面に入ったか監視
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          // 3) 20%以上見えたら.visibleクラスを付与 → フェードイン開始
          if (entry.isIntersecting) {
            entry.target.classList.add("visible");

            // 4) 一度表示したら監視解除（パフォーマンス最適化）
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.2 } // 閾値20%
    );

    // 5) 全.sectionを監視対象に追加
    sections.forEach((section) => observer.observe(section));

    // 6) クリーンアップ（コンポーネント破棄時に監視解除）
    return () => observer.disconnect();
  }, []);

  // -------------------------
  //  JSX: ページのレイアウト構造
  // -------------------------
  return (
    <>
      {/* ヘッダー（現在は未使用） */}
      {/* <Header /> */}

      {/* メインコンテンツ: 各セクションを描画 */}
      <main>
        <Message />
        <About />
        <Hobbies />
        <Skills />
      </main>

      {/* フッター（現在は未使用） */}
      {/* <Footer /> */}

      {/* トップに戻るボタン */}
      <button id="backToTop" className="back-to-top" aria-label="トップに戻る">
        ↑
      </button>

      {/* カスタムカーソル用のdiv */}
      <div id="cursor"></div>
    </>
  );
}
