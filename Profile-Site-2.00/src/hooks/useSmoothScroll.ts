// ナビのリンクをクリックしたときに、スムーズスクロールを実現するためのカスタムフック

const useSmoothScroll = () => {
  // 💡 イージング関数の定義（一定速度→ゆっくり終わる）
  // t: 経過時間、b: 開始値、c: 変化量、d: 期間
  // ここでは progress (0から1) を引数とする形式に変換
  const easeOutQuart = (progress: number) => {
    return 1 - Math.pow(1 - progress, 5); // 5乗（Quart）
  };

  const easeInOutCubic = (progress: number) => {
    return progress < 0.5
      ? 4 * progress * progress * progress
      : 1 - Math.pow(-2 * progress + 2, 3) / 2;
  };

  // 💡 イージングの種類を選択できる引数を追加
  const scrollToWithDuration = (
    targetId: string,
    duration: number,
    easingType: "easeOutQuart" | "easeInOutCubic" = "easeInOutCubic"
  ) => {
    const targetElement = document.getElementById(targetId);
    if (!targetElement) return;

    // ... (オフセット値や位置計算は省略なしでそのまま) ...
    const TOTAL_OFFSET = 112;

    const startPosition = window.pageYOffset;
    const rectTop = targetElement.getBoundingClientRect().top;
    const targetPosition = startPosition + rectTop - TOTAL_OFFSET;

    const distance = targetPosition - startPosition;
    let startTime: number | null = null;

    const animation = (currentTime: number) => {
      if (startTime === null) startTime = currentTime;
      const timeElapsed = currentTime - startTime;
      const progress = Math.min(timeElapsed / duration, 1);

      let easedProgress;

      // 💡 選択されたイージング関数を適用
      if (easingType === "easeOutQuart") {
        easedProgress = easeOutQuart(progress);
      } else {
        easedProgress = easeInOutCubic(progress);
      }

      // スクロール実行
      window.scrollTo(0, startPosition + distance * easedProgress);

      if (timeElapsed < duration) {
        window.requestAnimationFrame(animation);
      }
    };
    window.requestAnimationFrame(animation);
  };

  return { scrollToWithDuration };
};

export default useSmoothScroll;
