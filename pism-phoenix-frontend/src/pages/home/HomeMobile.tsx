import { useState, useEffect } from "react";
import {
    Users,
    ShieldCheck,
    BookOpen,
    History,
    LogOut,
    LayoutGrid,
    User
} from "lucide-react";
import { Card, CardTitle, CardDescription } from "@/components/ui/card";
import { logout } from "@/lib/auth";

interface Entry {
    id: string;
    title: string;
    description: string;
    icon: any;
    color: string;
    bg: string;
}

export default function HomeMobile() {
    const [user, setUser] = useState<{ account: string; email: string } | null>(null);
    const [activeEntryId, setActiveEntryId] = useState<string | null>(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("pism_user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const entries: Entry[] = [
        { id: "user-mgmt", title: "用户管理", description: "管理系统用户信息", icon: Users, color: "text-blue-600", bg: "bg-blue-50" },
        { id: "perms", title: "权限配置", description: "设置角色及功能权限", icon: ShieldCheck, color: "text-green-600", bg: "bg-green-50" },
        { id: "dict", title: "数据字典", description: "维护系统通用数据", icon: BookOpen, color: "text-orange-600", bg: "bg-orange-50" },
        { id: "logs", title: "日志审计", description: "查看系统操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" }
    ];

    const activeEntry = entries.find(e => e.id === activeEntryId);

    // Dashboard View (Mobile Portrait)
    if (activeEntryId === null) {
        return (
            <div className="min-h-screen bg-white p-6 flex flex-col">
                <div className="mt-8 mb-10 flex items-center justify-between">
                    <div className="space-y-1">
                        <h2 className="text-3xl font-black text-slate-900 tracking-tighter">探索中心</h2>
                        <p className="text-slate-400 font-medium text-sm">欢迎回来, {user?.account || "管理员"}</p>
                    </div>
                    <div className="h-12 w-12 rounded-2xl bg-primary text-white flex items-center justify-center font-black text-lg shadow-lg shadow-primary/20">
                        {user?.account?.charAt(0).toUpperCase() || "A"}
                    </div>
                </div>

                <div className="grid grid-cols-2 gap-4 flex-1 pb-10">
                    {entries.map((entry) => (
                        <Card
                            key={entry.id}
                            className="border-none bg-slate-50/50 rounded-[32px] p-6 flex flex-col items-center text-center justify-center space-y-4 shadow-sm active:scale-95 transition-all duration-300"
                            onClick={() => setActiveEntryId(entry.id)}
                        >
                            <div className={`${entry.bg} ${entry.color} p-4 rounded-2xl shadow-inner`}>
                                <entry.icon className="w-8 h-8" />
                            </div>
                            <div className="space-y-1">
                                <CardTitle className="text-lg font-black text-slate-900 tracking-tight">{entry.title}</CardTitle>
                                <CardDescription className="text-slate-400 text-[10px] font-medium leading-none px-2">{entry.description}</CardDescription>
                            </div>
                        </Card>
                    ))}

                    {/* Logout Link in Grid for Mobile */}
                    <Card
                        className="border-none bg-red-50/30 rounded-[32px] p-6 flex flex-col items-center text-center justify-center space-y-4 shadow-sm active:scale-95 transition-all duration-300"
                        onClick={logout}
                    >
                        <div className="bg-red-50 text-red-500 p-4 rounded-2xl">
                            <LogOut className="w-8 h-8" />
                        </div>
                        <CardTitle className="text-lg font-black text-red-600 tracking-tight">退出登录</CardTitle>
                    </Card>
                </div>
            </div>
        );
    }

    // Functional View (Mobile Portrait Shell)
    return (
        <div className="min-h-screen bg-white flex flex-col">
            <main className="flex-1 p-6 pb-32">
                {/* Minimal context indicator inside main to avoid header */}
                <div className="flex items-center justify-between mb-8 opacity-40">
                    <span className="font-black text-slate-900 tracking-tight text-[10px] uppercase">Pism Phoenix</span>
                    <span className="text-slate-900 font-bold text-[10px] tracking-widest uppercase">{activeEntryId}</span>
                </div>

                <div className="mb-10 space-y-4 animate-in slide-in-from-top-4 duration-500">
                    <div className={`${activeEntry?.bg || "bg-slate-50"} ${activeEntry?.color || "text-slate-400"} w-16 h-16 rounded-2xl flex items-center justify-center shadow-md`}>
                        {activeEntry && <activeEntry.icon className="w-8 h-8" />}
                    </div>
                    <div className="space-y-1">
                        <h2 className="text-3xl font-black text-slate-900 tracking-tight">{activeEntry?.title}</h2>
                        <p className="text-slate-400 font-medium text-sm leading-relaxed">{activeEntry?.description}</p>
                    </div>
                </div>

                {/* Module Workspace */}
                <div className="w-full min-h-[400px] bg-slate-50 rounded-[40px] border-2 border-dashed border-slate-100 flex flex-col items-center justify-center p-8 animate-in fade-in zoom-in-95 duration-700">
                    <div className="p-10 bg-white rounded-full shadow-inner mb-6">
                        <activeEntry.icon className="w-16 h-16 text-slate-100" />
                    </div>
                    <p className="text-slate-300 font-black text-sm tracking-widest uppercase text-center">Mobile Module Ready</p>
                </div>
            </main>

            {/* Bottom Navigation Bar */}
            <nav className="fixed bottom-8 left-6 right-6 h-20 bg-slate-900 shadow-[0_25px_50px_-12px_rgba(0,0,0,0.5)] rounded-[32px] flex items-center justify-between px-6 z-50 animate-in slide-in-from-bottom-12 duration-700">
                <button
                    onClick={() => setActiveEntryId(null)}
                    className="p-3 text-slate-500 hover:text-white transition-all active:scale-75"
                >
                    <LayoutGrid className="w-7 h-7" />
                </button>

                <div className="h-10 w-[1px] bg-white/10 mx-2" />

                <div className="flex-1 flex justify-around items-center px-2">
                    {entries.map((entry) => (
                        <button
                            key={entry.id}
                            onClick={() => setActiveEntryId(entry.id)}
                            className={`p-3 rounded-2xl transition-all duration-300 ${activeEntryId === entry.id ? "bg-primary text-white scale-110 shadow-lg shadow-primary/40 -translate-y-2" : "text-white/30"
                                }`}
                        >
                            <entry.icon className="w-7 h-7" />
                        </button>
                    ))}
                </div>

                <div className="h-10 w-[1px] bg-white/10 mx-2" />

                <button
                    onClick={logout}
                    className="p-3 text-white/30 hover:text-red-400 transition-all active:scale-75"
                >
                    <LogOut className="w-7 h-7" />
                </button>
            </nav>
        </div>
    );
}
