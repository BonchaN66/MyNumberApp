import { useEffect } from "react";
import Message from "./components/Message";
import About from "./components/About";
import Hobbies from "./components/Hobbies";
import Skills from "./components/Skills";
import RisingDots from "./components/RisingDots";
import "./styles/Section.css";

export default function Sections() {
  useEffect(() => {
    const sections = document.querySelectorAll(".section");
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("visible");
            observer.unobserve(entry.target);
          }
        });
      },
      { threshold: 0.2 }
    );

    sections.forEach((section) => observer.observe(section));
    return () => observer.disconnect();
  }, []);

  return (
    <main className="relative">
      {/* 背景画像 */}
      <div className="absolute inset-0 -z-10 bg-[url('/img/b-g.jpg')] bg-cover bg-center" />

      {/* 水玉 → 背景の上、テキストの下 */}
      <div className="absolute inset-0 z-0 pointer-events-none">
        <RisingDots />
      </div>

      {/* セクション本体（テキストやコンテンツ） */}
      <div className="relative z-10">
        <Message />
        <About />
        <Hobbies />
        <Skills />
      </div>
    </main>
  );
}
