/* LandingPage + HeroHeader */

import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { TypeAnimation } from "react-type-animation";
import useSmoothScroll from "../hooks/useSmoothScroll"; // 💡 カスタムフックをインポート
import "tailwindcss";

export default function LandingWithHero() {
  const [showIntro, setShowIntro] = useState(true);
  const [hasAutoScrolled, setHasAutoScrolled] = useState(false); // 💡 初回実行フラグを追加

  // 💡 カスタムフックを使用
  const { scrollToWithDuration } = useSmoothScroll();

  // タイピング中はスクロール無効
  useEffect(() => {
    // ランディングページの h-screen overflow-hidden との競合を避けるため、
    // bodyのoverflow制御は残します。
    document.body.style.overflow = showIntro ? "hidden" : "auto";
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [showIntro]);

  const handleIntroComplete = () => {
    setShowIntro(false);
  };

  // 💡 スクロールイベントを監視し、自動スクロールをトリガーするロジック
  useEffect(() => {
    // イントロ完了後、かつ自動スクロールがまだ実行されていない場合のみ監視を開始
    if (showIntro || hasAutoScrolled) return;

    // 💡 監視するスクロール位置の閾値 (例: 画面高さの約1/10)
    const SCROLL_THRESHOLD = window.innerHeight * 0.1;
    const AUTOSCROLL_DURATION = 1200; // 自動スクロールの速度 (1.2秒)

    const handleScroll = () => {
      // ユーザーが閾値を超えてスクロールしたら
      if (window.scrollY > SCROLL_THRESHOLD && !hasAutoScrolled) {
        // 1. 自動スクロールを実行
        scrollToWithDuration("message", AUTOSCROLL_DURATION, "easeInQuart"); // ゆっくり始まるイージングを使用

        // 2. フラグを立てて、二度と実行されないようにする
        setHasAutoScrolled(true);

        // 3. スクロールイベントの監視を停止
        window.removeEventListener("scroll", handleScroll);
      }
    };

    // イベントリスナーを追加
    window.addEventListener("scroll", handleScroll);

    // クリーンアップ関数: コンポーネントがアンマウントされるときにリスナーを削除
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, [showIntro, hasAutoScrolled, scrollToWithDuration]); // 依存配列に含める

  return (
    // 注意: ここに残っている overflow-hidden は、アニメーション完了後に削除推奨です
    <div className="relative w-full h-screen ">
      {/* イントロ */}
      <AnimatePresence>
        {showIntro && (
          <motion.div
            className="fixed inset-0 flex flex-col items-start md:text-4xl md:items-center justify-center px-10 
            bg-green-700 text-yellow-300 text-2xl font-bold z-20"
            // items-start + px-10で無理やりスマホ時に中央表示を実現
            initial={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 2.0, ease: "easeInOut" }}
          >
            <TypeAnimation
              sequence={["Welcome to My Portfolio ...", 1000, handleIntroComplete]}
              wrapper="span"
              cursor={true}
              repeat={0}
              className="type-animation"
            />
          </motion.div>
        )}
      </AnimatePresence>

      {/* HeroHeader */}

      {!showIntro && (
        // absolute -> relative に変更
        <header className="relative w-full h-screen overflow-hidden">
          {/* 背景画像（Ken Burns効果 + フェードイン） */}
          <motion.div
            className="fixed inset-0 bg-cover bg-center -z-30"
            style={{
              backgroundImage: "url('./img/first-view.jpg')",
            }}
            initial={{ opacity: 0, scale: 1 }}
            animate={{ opacity: 1, scale: 1.3 }}
            transition={{
              opacity: { duration: 2, ease: "easeInOut" },
              scale: { duration: 18, ease: "easeInOut" },
            }}
          />
          {/* 背景にグラデーション */}
          <div className="absolute inset-0 bg-gradient-to-t from-black via-transparent to-transparent" />
          {/* 3段タイトル */}
          <div className="relative flex flex-col items-center justify-center h-full w-full text-center gap-6">
            <motion.h1
              className="text-5xl md:text-7xl font-extrabold drop-shadow-lg text-white transform md:-translate-x-28"
              initial={{ opacity: 0, x: -60 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 1.8, ease: "easeOut", delay: 0.3 }}
            >
              BonchaN's
            </motion.h1>

            <motion.h1
              className="text-5xl md:text-7xl font-extrabold drop-shadow-lg text-white transform md:translate-x-28"
              initial={{ opacity: 0, x: 60 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 1.8, ease: "easeOut", delay: 0.6 }}
            >
              ProfileSite
            </motion.h1>

            <motion.h2
              className="text-6xl md:text-7xl font-extrabold drop-shadow-lg text-white"
              initial={{ opacity: 0, y: 0 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 2.5, ease: "easeOut", delay: 0.9 }}
            >
              ~Learning by building~
            </motion.h2>

            {/* ナビゲーション */}
            <nav className="mt-8 text-center items-center mx-auto">
              {/* 💡 レスポンシブなレイアウトと間隔の切り替え */}
              <ul className="flex flex-col space-y-4 md:inline-flex md:flex-row md:space-x-6 text-lg md:text-xl font-medium">
                <li>
                  <a
                    href="#message"
                    className="nav-link"
                    onClick={(e) => {
                      e.preventDefault();
                      scrollToWithDuration("message", 1500); // 💡 1500ms (1.5秒) で移動
                    }}
                  >
                    Message
                  </a>
                </li>

                <li>
                  <a
                    href="#about"
                    className="nav-link"
                    onClick={(e) => {
                      e.preventDefault();
                      scrollToWithDuration("about", 1300);
                    }}
                  >
                    About me
                  </a>
                </li>
                <li>
                  <a
                    href="#hobbies"
                    className="nav-link"
                    onClick={(e) => {
                      e.preventDefault();
                      scrollToWithDuration("hobbies", 1300);
                    }}
                  >
                    Hobbies
                  </a>
                </li>
                <li>
                  <a
                    href="#skills"
                    className="nav-link"
                    onClick={(e) => {
                      e.preventDefault();
                      scrollToWithDuration("skills", 1300);
                    }}
                  >
                    Skills
                  </a>
                </li>
              </ul>
            </nav>
          </div>
        </header>
      )}
    </div>
  );
}
