import { useToast } from "@/hooks/use-toast"
import { useDeviceMode } from "@/hooks/use-device-mode"
import {
  Toast,
  ToastClose,
  ToastDescription,
  ToastProvider,
  ToastTitle,
  ToastViewport,
} from "@/components/ui/toast"
import { cn } from "@/lib/utils"

export function Toaster() {
  const { toasts } = useToast()
  const mode = useDeviceMode()
  const isMobile = mode === "mobile"

  return (
    <ToastProvider>
      {toasts.map(function ({ id, title, description, action, className, ...props }: any) {
        return (
          <Toast
            key={id}
            className={cn(
              isMobile && "fixed bottom-24 left-1/2 -translate-x-1/2 w-[calc(100%-48px)] max-w-md rounded-2xl border-none shadow-2xl bg-slate-900/90 backdrop-blur-md text-white p-4 justify-center items-center text-center",
              className
            )}
            {...props}
          >
            <div className={cn("grid gap-1", isMobile && "text-center")}>
              {title && <ToastTitle className={cn(isMobile && "text-white font-bold")}>{title}</ToastTitle>}
              {description && (
                <ToastDescription className={cn(isMobile && "text-white/80 text-xs")}>{description}</ToastDescription>
              )}
            </div>
            {action}
            {!isMobile && <ToastClose />}
          </Toast>
        )
      })}
      <ToastViewport className={cn(isMobile && "bottom-24 flex-col items-center justify-center pointer-events-none")} />
    </ToastProvider>
  )
}
