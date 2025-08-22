import { motion } from "framer-motion";

const dots = Array.from({ length: 50 });

export default function RisingDots() {
  return (
    <div className="absolute inset-0 overflow-hidden pointer-events-none z-0">
      {dots.map((_, i) => {
        const size = Math.random() * 300;
        const duration = Math.random() * 25 + 25; // 25〜50秒
        const delay = -Math.random() * 20;
        const left = Math.random() * 100;

        return (
          <motion.div
            key={i}
            className="absolute rounded-full border border-white/50 bg-white/10" // 透明シャボン玉
            style={{
              width: size,
              height: size,
              left: `${left}%`,
              bottom: `-${size}px`,
            }}
            animate={{
              y: ["0%", "-400vh"],
              x: [0, Math.random() * 40 - 20, 0], // 左右にゆるく揺れる
              scale: [1, 1.05, 0.95, 1], // ほんの少し膨らむ感じ
              opacity: [0, 1, 0.8, 0], // 徐々に消える
            }}
            transition={{
              duration,
              repeat: Infinity,
              delay,
              ease: "easeInOut",
            }}
          />
        );
      })}
    </div>
  );
}
