// ナビのリンクをクリックしたときに、スムーズスクロールを実現するためのカスタムフック

const useSmoothScroll = () => {
  // 💡 新しいイージング関数の定義（ゆっくり始まって、速く終わる）
  const easeInQuart = (progress: number) => {
    // 4乗（Quart）を使用
    return progress * progress * progress * progress;
  };

  // 元の easeOutQuart は slow-end のため、ここで削除または名前を変更
  /*
  const easeOutQuart = (progress: number) => {
    return 1 - Math.pow(1 - progress, 5);
  };
  */

  const easeInOutCubic = (progress: number) => {
    return progress < 0.5
      ? 4 * progress * progress * progress
      : 1 - Math.pow(-2 * progress + 2, 3) / 2;
  };

  // 💡 イージングの種類を選択できる引数を更新
  const scrollToWithDuration = (
    targetId: string,
    duration: number,
    // 選択肢を更新: easeInQuart を追加
    easingType: "easeInQuart" | "easeInOutCubic" = "easeInOutCubic"
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
      if (easingType === "easeInQuart") {
        easedProgress = easeInQuart(progress);
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
