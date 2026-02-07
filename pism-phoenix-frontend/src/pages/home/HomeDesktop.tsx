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
import { Button } from "@/components/ui/button";

interface Entry {
    id: string;
    title: string;
    description: string;
    icon: any;
    color: string;
    bg: string;
}

export default function HomeDesktop() {
    const [user, setUser] = useState<{ account: string; email: string } | null>(null);
    const [activeEntryId, setActiveEntryId] = useState<string | null>(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("pism_user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const entries: Entry[] = [
        { id: "user-mgmt", title: "用户管理", description: "管理系统用户信息及状态", icon: Users, color: "text-blue-600", bg: "bg-blue-50" },
        { id: "perms", title: "权限配置", description: "设置系统角色及功能权限", icon: ShieldCheck, color: "text-green-600", bg: "bg-green-50" },
        { id: "dict", title: "数据字典", description: "维护系统通用数据及常量", icon: BookOpen, color: "text-orange-600", bg: "bg-orange-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" },
        { id: "logs", title: "日志审计", description: "查看系统运行及操作日志", icon: History, color: "text-purple-600", bg: "bg-purple-50" }
    ];

    const activeEntry = entries.find(e => e.id === activeEntryId);

    if (activeEntryId === null) {
        return (
            <div className="min-h-screen bg-slate-50 flex items-center justify-center p-12 md:p-24 animate-in fade-in zoom-in-95 duration-700">
                <div className="max-w-7xl w-full grid grid-cols-5 gap-8">
                    {/* User Card */}
                    <Card className="border-none shadow-2xl bg-white rounded-[40px] p-8 flex flex-col justify-between min-h-[340px] group border-t-4 border-primary">
                        <div className="space-y-6">
                            <div className="h-20 w-20 rounded-[28px] bg-primary/10 text-primary flex items-center justify-center font-black text-4xl shadow-inner ring-4 ring-slate-50 transition-transform group-hover:scale-110 duration-500">
                                {user?.account?.charAt(0).toUpperCase() || "A"}
                            </div>
                            <div>
                                <h3 className="text-3xl font-black text-slate-900 tracking-tighter truncate">{user?.account || "管理员"}</h3>
                                <p className="text-slate-400 font-medium text-lg truncate">{user?.email || "admin@pism.com"}</p>
                            </div>
                        </div>
                        <Button
                            variant="ghost"
                            className="w-full h-16 rounded-[24px] text-slate-400 hover:text-red-600 hover:bg-red-50 text-xl font-bold mt-auto border border-dashed border-slate-200"
                            onClick={logout}
                        >
                            <LogOut className="h-7 w-7 mr-3" />
                            退出登录
                        </Button>
                    </Card>

                    {/* Functional Entry Cards */}
                    {entries.map((entry) => (
                        <Card
                            key={entry.id}
                            className="border-none shadow-2xl hover:shadow-primary/5 hover:translate-y-[-8px] transition-all duration-500 cursor-pointer group rounded-[40px] flex flex-col p-8 bg-white"
                            onClick={() => setActiveEntryId(entry.id)}
                        >
                            <div className={`${entry.bg} ${entry.color} p-6 w-24 h-24 rounded-[32px] mb-10 flex items-center justify-center transition-all group-hover:rotate-12 group-hover:scale-110 duration-500 shadow-sm`}>
                                <entry.icon className="w-12 h-12" />
                            </div>
                            <CardTitle className="text-3xl font-black text-slate-900 tracking-tighter mb-3">{entry.title}</CardTitle>
                            <CardDescription className="text-slate-400 text-xl font-medium leading-tight line-clamp-2">{entry.description}</CardDescription>
                            <div className="mt-auto pt-10 px-2">
                                <div className="h-1.5 w-12 bg-slate-100 rounded-full transition-all group-hover:w-full duration-700 ease-in-out opacity-40">
                                    <div className={`h-full w-full ${entry.bg.replace('-50', '-200')} rounded-full`}></div>
                                </div>
                            </div>
                        </Card>
                    ))}
                </div>
            </div>
        );
    }

    return (
        <div className="flex h-screen bg-white overflow-hidden animate-in fade-in duration-500">
            <aside className="w-24 bg-slate-50 border-r border-slate-100 flex-col items-center py-10 z-30 flex">
                <div className="mb-12 group cursor-default">
                    <div className="h-14 w-14 rounded-2xl bg-white shadow-md text-primary flex items-center justify-center font-black text-xl ring-2 ring-slate-100 ring-offset-2 transition-all group-hover:scale-110">
                        {user?.account?.charAt(0).toUpperCase() || <User className="w-6 h-6" />}
                    </div>
                </div>
                <nav className="flex-1 flex flex-col gap-6">
                    {entries.map((entry) => (
                        <button
                            key={entry.id}
                            onClick={() => setActiveEntryId(entry.id)}
                            className={`p-4 rounded-2xl transition-all duration-300 relative group ${activeEntryId === entry.id ? "bg-primary text-white shadow-xl shadow-primary/30 scale-110" : "bg-white text-slate-400 hover:text-slate-900 hover:shadow-md"
                                }`}
                        >
                            <entry.icon className="h-8 w-8" />
                            {activeEntryId === entry.id && <span className="absolute -right-3 top-1/2 -translate-y-1/2 w-1.5 h-6 bg-primary rounded-full" />}
                        </button>
                    ))}
                </nav>
                <div className="mt-auto flex flex-col gap-6">
                    <button onClick={() => setActiveEntryId(null)} className="p-4 rounded-2xl bg-white text-slate-400 hover:text-primary hover:shadow-md transition-all duration-300">
                        <LayoutGrid className="h-8 w-8" />
                    </button>
                    <button onClick={logout} className="p-4 rounded-2xl bg-white text-slate-400 hover:text-red-600 hover:shadow-md transition-all duration-300">
                        <LogOut className="h-8 w-8" />
                    </button>
                </div>
            </aside>
            <main className="flex-1 relative bg-white overflow-y-auto">
                <div className="max-w-6xl mx-auto p-20">
                    <div className="flex items-center gap-10 mb-20 animate-in slide-in-from-left-8 duration-700">
                        <div className={`${activeEntry?.bg} ${activeEntry?.color} p-8 w-28 h-28 rounded-[40px] flex items-center justify-center shadow-xl shadow-inner`}>
                            {activeEntry && <activeEntry.icon className="w-14 h-14" />}
                        </div>
                        <div className="space-y-2">
                            <div className="flex items-center gap-3">
                                <span className={`h-3 w-3 rounded-full ${activeEntry?.color.replace('text-', 'bg-')} animate-pulse`} />
                                <span className="text-slate-400 font-bold tracking-widest uppercase text-sm">{activeEntry?.id}</span>
                            </div>
                            <h2 className="text-6xl font-black text-slate-900 tracking-tighter leading-none">{activeEntry?.title}</h2>
                            <p className="text-slate-400 text-2xl font-medium tracking-tight">{activeEntry?.description}</p>
                        </div>
                    </div>
                    <div className="w-full min-h-[500px] border-4 border-dashed border-slate-50 rounded-[60px] flex items-center justify-center animate-in fade-in slide-in-from-bottom-8 duration-1000">
                        <div className="text-center space-y-8 p-6">
                            <div className="p-12 bg-slate-50 rounded-[50px] inline-block shadow-inner ring-1 ring-slate-100">
                                {activeEntry && <activeEntry.icon className="w-24 h-24 text-slate-200" />}
                            </div>
                            <div className="space-y-4">
                                <p className="text-slate-300 text-4xl font-black tracking-tighter uppercase italic opacity-50">Experimental Module</p>
                                <p className="text-slate-200 text-xl font-bold">Pism Phoenix Desktop Core Integration</p>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
}
