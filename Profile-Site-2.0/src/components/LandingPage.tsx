import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { TypeAnimation } from "react-type-animation";
import "tailwindcss";

export default function LandingWithHero() {
  const [showIntro, setShowIntro] = useState(true);

  // タイピング中はスクロール無効
  useEffect(() => {
    document.body.style.overflow = showIntro ? "hidden" : "auto";
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [showIntro]);

  const handleIntroComplete = () => {
    setShowIntro(false);
  };

  return (
    <div className="relative w-full h-screen overflow-hidden">
      {/* イントロ */}
      <AnimatePresence>
        {showIntro && (
          <motion.div
            className="fixed inset-0 flex flex-col items-center justify-center bg-green-700 text-yellow-300 text-4xl font-bold z-20"
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
        <header id="hero-header" className="relative w-full h-screen">
          {/* 背景画像（Ken Burns効果 + フェードイン） */}
          <motion.div
            className="absolute inset-0 bg-cover bg-center"
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

          {/* グラデーションオーバーレイ */}
          <div className="absolute inset-0 bg-gradient-to-t from-black via-transparent to-transparent" />

          {/* テキスト（二段タイトル） */}
          <div className="relative z-10 flex flex-col justify-center h-full w-full px-100">
            <motion.h1
              className="text-6xl md:text-7xl font-extrabold drop-shadow-lg text-[white]"
              initial={{ opacity: 0, x: -30 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 1.2, ease: "easeOut", delay: 0.3 }}
            >
              BonchaN's
            </motion.h1>

            <motion.h1
              className="text-6xl md:text-7xl font-extrabold drop-shadow-lg text-[white] mt-6 px-45"
              initial={{ opacity: 0, x: 30 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 1.2, ease: "easeOut", delay: 0.9 }}
            >
              ProfileSite
            </motion.h1>

            {/* ナビゲーション */}
            <nav className="mt-8 text-center items-center mx-auto">
              <ul className="inline-flex space-x-6 text-lg md:text-xl font-medium">
                {/* ↑なんか怒られてるけど動く、前はこれでエラー吐かなかった */}
                <li>
                  <a href="#message" className="nav-link">
                    Message
                  </a>
                </li>
                <li>
                  <a href="#about" className="nav-link">
                    About me
                  </a>
                </li>
                <li>
                  <a href="#hobbies" className="nav-link">
                    Hobbies
                  </a>
                </li>
                <li>
                  <a href="#skills" className="nav-link">
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
