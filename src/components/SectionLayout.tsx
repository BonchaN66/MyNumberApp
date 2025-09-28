// src/components/SectionLayout.tsx
import React from "react";
import { easeInOut, motion } from "framer-motion";

// Propsの型定義
interface SectionLayoutProps {
  imgSrc: string; // 画像パス
  reverse: boolean; // レイアウト反転フラグ
  children: React.ReactNode; // テキストコンテンツ（子要素として受け取る）
}

// 画像/テキストのアニメーションを定義
const layoutVariants = {
  left: { opacity: 0, x: -70 },
  right: { opacity: 0, x: 70 },
  visible: { opacity: 1, x: 0, transition: { duration: 1.6, ease: easeInOut } },
};

const SectionLayout: React.FC<SectionLayoutProps> = ({ imgSrc, reverse, children }) => {
  return (
    // 💡 1. メインレイアウトとアニメーション
    <motion.div
      // 修正: justify-between -> justify-center に変更し、gapを大きくする
      className={`
        flex items-center justify-center gap-16
        max-md:flex-col max-md:items-center max-md:gap-8 pt-20 pb-20
        ${reverse ? "flex-row-reverse" : "flex-row"}
      `}
      initial={reverse ? "right" : "left"}
      whileInView="visible"
      variants={layoutVariants}
      viewport={{ once: true, amount: 0.4 }} // ビューポートに40%入ったら発火
    >
      {/* 2. テキストラッパー (子のコンテンツを受け取る) */}
      <div
        // 役割は維持: 最大幅を制限しつつ、柔軟に幅を調整
        className="flex-1 max-w-[500px] max-md:w-full max-md:text-center"
      >
        {children}
      </div>

      {/* 3. 画像ラッパー (画像とサイズ統一) */}
      <div
        // 役割は維持: サイズ統一のロジック
        className="
          w-full max-w-[650px]
          aspect-[16/10]
          rounded-lg overflow-hidden
          max-md:w-11/12 max-md:my-4
        "
      >
        <img src={imgSrc} alt="Section image" className="w-full h-full object-cover" />
      </div>
    </motion.div>
  );
};
export default SectionLayout;
