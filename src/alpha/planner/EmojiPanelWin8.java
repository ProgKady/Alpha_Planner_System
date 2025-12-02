package alpha.planner;



import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.Win32VK;

public class EmojiPanelWin8 {

    public static void openEmojiPanel() {
        User32 user32 = User32.INSTANCE;

        // Allocate contiguous memory for 4 INPUT structures
        WinUser.INPUT[] inputs = (WinUser.INPUT[]) new WinUser.INPUT().toArray(4);

        // Win key down
        inputs[0].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[0].input.setType("ki");
        inputs[0].input.ki.wVk = new WinDef.WORD(Win32VK.VK_LWIN.code);

        // '.' down
        inputs[1].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[1].input.setType("ki");
        inputs[1].input.ki.wVk = new WinDef.WORD(Win32VK.VK_OEM_PERIOD.code);

        // '.' up
        inputs[2].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[2].input.setType("ki");
        inputs[2].input.ki.wVk = new WinDef.WORD(Win32VK.VK_OEM_PERIOD.code);
        inputs[2].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP);

        // Win key up
        inputs[3].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
        inputs[3].input.setType("ki");
        inputs[3].input.ki.wVk = new WinDef.WORD(Win32VK.VK_LWIN.code);
        inputs[3].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP);

        // Send sequence
        user32.SendInput(new WinDef.DWORD(inputs.length), inputs, inputs[0].size());
    }

    public static void main(String[] args) {
        openEmojiPanel();
    }
}

