import { useState, useEffect } from "react";

export type DeviceMode = "mobile" | "desktop";

export function useDeviceMode(): DeviceMode {
    const [mode, setMode] = useState<DeviceMode>("desktop");

    useEffect(() => {
        const checkMode = () => {
            const width = window.innerWidth;
            // Robust Mobile Phone Detection:
            // 1. Must have touch points (distinguishes from most computer browsers)
            // 2. Logical width must be less than 500px (strictly targets smartphones, excludes all tablets/iPads)
            // 3. Explicitly exclude 'Macintosh' UA which iPads often report as
            const isTouch = ('ontouchstart' in window) || (navigator.maxTouchPoints > 0);
            const isIPad = /iPad|Macintosh/i.test(navigator.userAgent) && isTouch;

            // Phones are typically < 500px logical width. 
            // Tablets (including iPad Mini) are typically >= 768px.
            if (isTouch && width < 500 && !isIPad) {
                setMode("mobile");
            } else {
                setMode("desktop");
            }
        };

        checkMode();
        window.addEventListener("resize", checkMode);
        window.addEventListener("orientationchange", checkMode);

        return () => {
            window.removeEventListener("resize", checkMode);
            window.removeEventListener("orientationchange", checkMode);
        };
    }, []);

    return mode;
}
