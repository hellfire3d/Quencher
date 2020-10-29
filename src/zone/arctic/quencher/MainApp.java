package zone.arctic.quencher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;

public class MainApp {

    public static void main(String[] argz) {

        /*
        String[] args = {"port", 

            "D:\\Downloads\\seaside.farc",
            "D:\\Downloads\\seaside.map",
            "D:\\Downloads\\seaside.farc",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\test2.map",
            "E8 75 CD 8B A3 58 56 5A 5E AF 80 5D 0E 80 BF 1E 31 B3 76 7E",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\test3.farc",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\test3.map"
            };
        */
        
        /*
        String[] args = {"compress",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\pal_usa_alley_1119.plan.out",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\finished\\pal_usa_alley_1119.plan",
            "PLNb", //header
            "00 00 01 DD", //rev number
            "01 00 01 00", //extra header data
            "00 00 00 05 02 13 37 67 5C 00 00 00 02 02 13 37 67 5D 00 00 00 01 02 13 37 AF 33 00 00 00 07 02 00 00 26 94 00 00 00 02 02 00 00 26 95 00 00 00 02" //dep example, copy this line for more line for more
        };
        */
        String[] args = {"build_farc",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\finished",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\finished.farc"
        };
        
        /*Z
        String[] args = {"extract", 

            "D:\\Downloads\\seaside.farc",
            "D:\\Downloads\\seaside.map",
            "EC 7F 52 95 7B 08 52 4E CF 4F 32 13 3F 6C 7F EC B8 BA 3E 27",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\test2.farc",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\costume\\test2.map"
            };
        */
        
        //String[] args = {"patch_map",
            
        //    "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\dev_hdd0\\game\\NPUA80472\\USRDIR\\output\\blurayguids.map",
        //    "Y:\\Documents\\LBP_Archives\\PrehistoricMoves\\blurayguids.map",
        //    "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\dev_hdd0\\game\\NPUA80472\\USRDIR\\output\\cool.map"
                
        //};
        
          
        
        /*
        String[] args = {"patch_alpha",
            "I:\\Documents\\LBP_Archives\\LBP3_Alpha\\BCES01663\\USRDIR\\data.farc",
            "I:\\Documents\\LBP_Archives\\LBP3_Alpha\\BCES01663\\USRDIR\\output\\blurayguids.map",
            "D:\\test\\alpha_newrevs\\data.farc",
            "D:\\test\\alpha_newrevs\\blurayguids.map"
        };
        */
        
        /*
        String[] args = {"patch_farc",
            
            //"A:\\Emulator\\rpcs3-v0.0.7-9128-8e39c778_win64\\dev_hdd0\\game\\NPUA80662\\USRDIR\\data.farc",
            //"D:\\Desktop\\UP9000-NPUA70117_00-GBETALITTL000001-A0101-V0100-PE\\NPUA70117\\USRDIR\\patches\\patch_0101.farc",
            //"A:\\Emulator\\rpcs3-v0.0.7-9128-8e39c778_win64\\dev_hdd0\\game\\NPUA80662\\USRDIR\\data_lbp2beta.farc"
            //"A:\\test\\data_woscripts.farc",
            //"I:\\Documents\\LBP_Archives\\LBP3_Alpha\\BCES01663\\USRDIR\\cum.edat",
            //"A:\\test\\data_wscripts.farc"
                
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\dev_hdd0\\game\\NPUA80472\\USRDIR\\data.farc",
            "D:\\Downloads\\seaside.farc",
            "E:\\Emulator\\rpcs3-v0.0.12-11059-b9988523_win64\\dev_hdd0\\game\\NPUA80472\\USRDIR\\data_002.farc"
        };
        */
        
        
        //String[] args = {"revnumbers_alpha",
        //    "Y:\\Documents\\LBP_Archives\\LBP3_Alpha\\BCES01663\\USRDIR\\data.farc",
        //    "Y:\\Documents\\LBP_Archives\\LBP3_Alpha\\BCES01663\\USRDIR\\output\\blurayguids.map",
        //};
        
        
        //String[] args = {"revnumbers_alpha",
        //    "D:\\Downloads\\seaside.farc",
        //    "D:\\Downloads\\seaside.map",
        //};
        
        switch (args[0]) {
            case "port":
                if (args.length==8) {
                    
                    File farcSrc = new File(args[1]);
                    File farcSrc2 = new File(args[3]);
                    File mapSrc = new File(args[2]);
                    File mapSrc2 = new File(args[4]);
                    
                    File farcOut = new File(args[6]);
                    File mapOut = new File(args[7]);
                    
                    
                    if (mapOut.exists() || farcOut.exists()) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("WARNING: this will manipulate the following files (make backups):");
                        if (farcOut.exists())
                            System.out.println(args[6]);
                        if (mapOut.exists())
                            System.out.println(args[7]);
                        System.out.println("Are you sure you want to proceed? (type y): ");

                        String input = scan.nextLine();
                        if (!input.equals("y")) {
                            break;
                        }
                    }

                    
                    if (farcSrc.exists() && farcSrc2.exists() && mapSrc.exists() && mapSrc2.exists()) {
                        if (port(farcSrc, mapSrc, farcSrc2, mapSrc2, args[5].replace(" ", ""), farcOut, mapOut)) {
                            break;
                        } else {
                            System.out.println("error porting");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "extract":
                if (args.length==6) {
                    
                    File farcSrc = new File(args[1]);
                    File mapSrc = new File(args[2]);
                    
                    File farcOut = new File(args[4]);
                    File mapOut = new File(args[5]);
                    
                    
                    if (mapOut.exists() || farcOut.exists()) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("WARNING: this will manipulate the following files (make backups):");
                        if (farcOut.exists())
                            System.out.println(args[6]);
                        if (mapOut.exists())
                            System.out.println(args[7]);
                        System.out.println("Are you sure you want to proceed? (type y): ");

                        String input = scan.nextLine();
                        if (!input.equals("y")) {
                            break;
                        }
                    }

                    
                    if (farcSrc.exists() && mapSrc.exists() ) {
                        if (extract(farcSrc, mapSrc, args[3].replace(" ", ""), farcOut, mapOut)) {
                            break;
                        } else {
                            System.out.println("error extracting");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "patch_map":
                if (args.length==4) {
                    
                    File mapSrc = new File(args[1]);
                    File mapSrc2 = new File(args[2]);
                    File mapOut = new File(args[3]);
                    
                    
                    if (mapOut.exists()) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("WARNING: this will manipulate the following files (make backups):");
                        if (mapOut.exists())
                            System.out.println(args[3]);
                        System.out.println("Are you sure you want to proceed? (type y): ");

                        String input = scan.nextLine();
                        if (!input.equals("y")) {
                            break;
                        }
                    }

                    
                    if (mapSrc.exists() && mapSrc2.exists()) {
                        if (patchMap(mapSrc, mapSrc2, mapOut)) {
                            break;
                        } else {
                            System.out.println("error patching");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "patch_farc":
                if (args.length==4) {
                    
                    File farcSrc = new File(args[1]);
                    File farcSrc2 = new File(args[2]);
                    File farcOut = new File(args[3]);
                    
                    
                    if (farcOut.exists()) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("WARNING: this will manipulate the following files (make backups):");
                        if (farcOut.exists())
                            System.out.println(args[3]);
                        System.out.println("Are you sure you want to proceed? (type y): ");

                        String input = scan.nextLine();
                        if (!input.equals("y")) {
                            break;
                        }
                    }

                    if (farcSrc.exists() && farcSrc2.exists()) {
                        if (patchFarc(farcSrc, farcSrc2, farcOut)) {
                            break;
                        } else {
                            System.out.println("error patching");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "patch_alpha":
                if (args.length==5) {
                    
                    File farcSrc = new File(args[1]);
                    File mapSrc = new File(args[2]);
                    File farcOut = new File(args[3]);
                    File mapOut = new File(args[4]);
                    
                    if (mapOut.exists() || farcOut.exists()) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("WARNING: this will manipulate the following files (make backups):");
                        if (farcOut.exists())
                            System.out.println(args[3]);
                        if (mapOut.exists())
                            System.out.println(args[4]);
                        System.out.println("Are you sure you want to proceed? (type y): ");

                        String input = scan.nextLine();
                        if (!input.equals("y")) {
                            break;
                        }
                    }

                    if (farcSrc.exists() && mapSrc.exists()) {
                        if (patchLBP3Alpha(farcSrc, mapSrc, farcOut, mapOut)) {
                            break;
                        } else {
                            System.out.println("error patching");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "revnumbers_alpha":
                if (args.length==3) {
                    
                    File farcSrc = new File(args[1]);
                    File mapSrc = new File(args[2]);

                    if (farcSrc.exists() && mapSrc.exists()) {
                        if (getLBP3AlphaRevNumbers(farcSrc, mapSrc)) {
                            break;
                        } else {
                            System.out.println("error");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "compress":
                if (args.length==7) {
                    
                    File fileSrc = new File(args[1]);
                    File outDest = new File(args[2]);
                    String header = args[3];
                    byte[] revnumber = MiscUtils.hexStringToByteArray(args[4].replace(" ", ""));
                    byte[] headerstuff = MiscUtils.hexStringToByteArray(args[5].replace(" ", ""));
                    byte[] footer = MiscUtils.hexStringToByteArray(args[6].replace(" ", ""));
                    
                    if (fileSrc.exists()) {
                        if (compress(fileSrc, outDest, header, revnumber, headerstuff, footer)) {
                            break;
                        } else {
                            System.out.println("error");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            case "build_farc":
                if (args.length==3) {
                    
                    File folderSrc = new File(args[1]);
                    File outDest = new File(args[2]);
                    
                    if (folderSrc.exists() && folderSrc.isDirectory()) {
                        if (build_farc(folderSrc, outDest)) {
                            break;
                        } else {
                            System.out.println("error");
                        }
                    } else {
                        System.out.println("file not found");
                        break;
                    }
                    
                }
            default:
                System.out.println("usage: quencher port farc map farc_from map_from guid/hash farc_out map_out");
                System.out.println("quencher patch_map map map_from map_out");
                System.out.println("quencher patch_farc farc farc_from farc_out");
        }
        
    }
    
    public static boolean build_farc(File folderSrc, File outDest) {
        return true;
    }
    
    public static boolean compress(File fileSrc, File outDest, String header, byte[] revnumber, byte[] headerstuff, byte[] footer) {
        try {
            InputStream is = new FileInputStream(fileSrc);
            OutputStream stream = new FileOutputStream(outDest);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            int size = 0;
            
            long ThisIsHowBigItIs = Files.size(fileSrc.toPath());
            
            long howManyTimes = 0;
            long lastSize = 0;
            for (int i=0; i<ThisIsHowBigItIs; i+=32768) {
                if (i+32768>ThisIsHowBigItIs) {
                    lastSize = ThisIsHowBigItIs - i;
                }
                howManyTimes++;
            }
            
            int[] zlibSizesUncompressed = new int[(int)howManyTimes];
            int[] zlibSizesCompressed = new int[(int)howManyTimes];
            
            for (int i=0; i<howManyTimes; i++) {
                byte[] input;
                if (i==howManyTimes-1) {
                    input = new byte[(int)lastSize];
                    zlibSizesUncompressed[i] = (int)lastSize;
                } else {
                    input = new byte[32768];
                    zlibSizesUncompressed[i] = 32768;
                }
                
                is.read(input);
                
                byte[] output = new byte[32768];
                
                Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
                deflater.setInput(input);
                deflater.finish();
                
                int compressedSize = deflater.deflate(output);
                zlibSizesCompressed[i] = compressedSize;
                
                bytes.write(output, 0, compressedSize);
                size+=deflater.getBytesWritten();
                deflater.end();
                
            }
            stream.write(header.getBytes());
            stream.write(revnumber);

            byte[] offset = MiscUtils.longToByteArray(header.length() + revnumber.length + 4 + headerstuff.length + 1 + 4*howManyTimes + bytes.size());
            stream.write(offset); 
            stream.write(headerstuff);
            stream.write(new Integer((int)howManyTimes).byteValue());
            for (int i=0; i<howManyTimes; i++) {
                byte[] ret = new byte[2];
                ret[0] = (byte)((zlibSizesCompressed[i] >> 8) & 0xff);
                ret[1] = (byte)(zlibSizesCompressed[i] & 0xff);
                stream.write(ret);
                ret = new byte[2];
                ret[0] = (byte)((zlibSizesUncompressed[i] >> 8) & 0xff);
                ret[1] = (byte)(zlibSizesUncompressed[i] & 0xff);
                stream.write(ret);
            }
            stream.write(bytes.toByteArray());
            stream.write(footer);
            stream.close();
            
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean patchMap(File mapSrc, File mapSrc2, File mapOut) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Patch duplicates with contents from " + mapSrc2.getName() + "? (type y/n, default y):");

        String input = scan.nextLine();
        boolean patchMePlease = true;
        if (input.equals("n")) 
            patchMePlease = false;
        
        
        if (!mapSrc.exists() || !mapSrc2.exists()) return false;
        MapFile mapFileSrc = parseMapFile(mapSrc);
        MapFile mapFileSrc2 = parseMapFile(mapSrc2);
        
        for (MapEntry entryPatch : mapFileSrc2.entries) {
            boolean pass = true;
            for (MapEntry entryBase : mapFileSrc.entries) {
                if (entryPatch.getGUID() == entryBase.getGUID()) {
                    if (patchMePlease==false) {
                        //System.out.println("Entry " + entryPatch.getPath() + " already found, skipping...");   
                    } else {
                        //System.out.println("Entry " + entryPatch.getPath() + " already found, patching...");   
                        entryBase.setHash(entryPatch.getHash());
                    }
                    pass = false;
                }
            }
            if (pass==true) {
                //System.out.println("Adding " + entryPatch.getPath() + " to map");  
                    mapFileSrc.addEntry(entryPatch);
            }
        }
        
        mapFileSrc.sortEntries();
        exportMapToFile(mapFileSrc, mapOut);
        return true;
    }
    
    public static boolean patchFarc(File farcSrc, File farcSrc2, File farcOut) {

        if (!farcSrc.exists() || !farcSrc2.exists()) return false;
        FarcFile farcFileSrc = parseFarcFile(farcSrc);
        FarcFile farcFileSrc2 = parseFarcFile(farcSrc2);
        
        try {
            
            RandomAccessFile rafFarc1 = new RandomAccessFile(farcFileSrc.farcData, "r");
            RandomAccessFile rafFarc2 = new RandomAccessFile(farcFileSrc2.farcData, "r");
            RandomAccessFile rafDest = new RandomAccessFile(farcOut, "rw");
            rafDest.setLength(farcFileSrc.entriesOffset + farcFileSrc2.entriesOffset);
            
            FileChannel farcFC1 = rafFarc1.getChannel();
            FileChannel farcFC2 = rafFarc2.getChannel();
            FileChannel dfc = rafDest.getChannel();

            dfc.transferFrom(farcFC1, 0, farcFileSrc.entriesOffset);
            dfc.transferFrom(farcFC2, farcFileSrc.entriesOffset, farcFileSrc2.entriesOffset);

            rafDest.seek(farcFileSrc.entriesOffset + farcFileSrc2.entriesOffset);
            
            FarcFile output = new FarcFile();
            
            for (FarcEntry entry : farcFileSrc.entries) {
                output.addEntry(entry);
            }
            
            for (FarcEntry entry : farcFileSrc2.entries) {
                FarcEntry newEntry = new FarcEntry(entry.getHash(), entry.getOffset() + farcFileSrc.entriesOffset, entry.getSize());
                output.addEntry(newEntry);
            }
            
            output.sortEntries();
            
            for (FarcEntry entry : output.entries) {
                rafDest.write(entry.getHash());
                rafDest.write(MiscUtils.longToByteArray(entry.getOffset()));
                rafDest.write(MiscUtils.longToByteArray(entry.getSize()));
            }
            rafDest.writeInt(output.entries.size());
            rafDest.write(new byte[]{0x46, 0x41, 0x52, 0x43});
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    
    public static boolean patchLBP3Alpha(File farcSrc, File mapSrc, File farcOut, File mapOut) {
        FarcFile farcFileSrc = parseFarcFile(farcSrc);
        MapFile mapFileSrc = parseMapFile(mapSrc);
        try {
            
            RandomAccessFile rafFarc1 = new RandomAccessFile(farcFileSrc.farcData, "r");
            RandomAccessFile rafDest = new RandomAccessFile(farcOut, "rw");

            FileChannel farcFC1 = rafFarc1.getChannel();
            FileChannel dfc = rafDest.getChannel();

            dfc.transferFrom(farcFC1, 0, farcFileSrc.entriesOffset);

            rafDest.seek(farcFileSrc.entriesOffset);
            
            FarcFile outputFarc = new FarcFile();
            MapFile outputMap = new MapFile();
            
            ArrayList<SHA1Patch> patchables = new ArrayList<>();
            
            for (FarcEntry entry : farcFileSrc.entries) {
                outputFarc.addEntry(entry);
            }
            for (MapEntry entry : mapFileSrc.entries) {
                outputMap.addEntry(entry);
            }
            
            for (MapEntry entry : mapFileSrc.entries) {
                if (entry.getPath().contains(".mol") || 
                        entry.getPath().contains(".plan") || 
                        entry.getPath().contains(".smh") || 
                        entry.getPath().contains(".bin") ||
                        entry.getPath().contains(".anim") ||
                        entry.getPath().contains(".gmat") ||
                        entry.getPath().contains(".bev") ) {
                    
                    //System.out.println("im on " + MiscUtils.byteArrayToHexString(entry.getHash()));
                    byte[] bytesToRead = pullFromFarc(entry.getHash(), farcFileSrc, mapFileSrc);
                    if (bytesToRead==null) {
                        System.out.println("Fuck");
                        continue;
                    }
                    ByteArrayInputStream fileAccess = new ByteArrayInputStream(bytesToRead);
                    fileAccess.skip(4);
                    byte[] revNumb = new byte[4];
                    fileAccess.read(revNumb);
                    
                    long revisionLong = MiscUtils.byteArrayToLong(revNumb);
                    if (revisionLong > MiscUtils.byteArrayToLong(MiscUtils.hexStringToByteArray("000003F8")) ) {
                        //System.out.println(revisionLong + " > " + MiscUtils.byteArrayToLong(MiscUtils.hexStringToByteArray("000003F8")));
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        fileAccess.reset();
                        byte[] header = new byte[4];
                        fileAccess.read(header);
                        byteOut.write(header);
                        
                        fileAccess.skip(4);
                        revNumb[0] = 0x00;
                        revNumb[1] = 0x00;
                        byteOut.write(revNumb);
                        
                        byte[] theRestOfIt = new byte[bytesToRead.length-8];
                        fileAccess.read(theRestOfIt);
                        byteOut.write(theRestOfIt);
                        
                        long offsetOfFarcReplace = 0;
                        for (FarcEntry farcEntry : farcFileSrc.entries) {
                            if (Arrays.equals(farcEntry.getHash(), entry.getHash())) {
                                offsetOfFarcReplace = farcEntry.getOffset();
                                rafDest.seek(offsetOfFarcReplace);
                                rafDest.write(byteOut.toByteArray());
                                
                                String SHA1new = MiscUtils.SHAsum(byteOut.toByteArray());
                                //System.out.println("patching " + entry.getPath() + " at pos " + offsetOfFarcReplace + " - old sha1: " + MiscUtils.byteArrayToHexString(entry.getHash()) + ", new sha1: " + SHA1new);
                                
                                byte[] newSha1 = MiscUtils.hexStringToByteArray(SHA1new);
                                patchables.add(new SHA1Patch(entry.getHash(), newSha1));
                                
                                
                                //entry.setHash(newSha1);
                                //farcEntry.setHash(newSha1);
                            }
                        }
                        
                    }
                    fileAccess.close();
                }
            }

            for (SHA1Patch patch : patchables) {
                for (FarcEntry outputEntry : outputFarc.entries) {
                    if (Arrays.equals(patch.old1, outputEntry.getHash())) {
                        outputEntry.setHash(patch.new1);
                    }
                }
                for (MapEntry outputEntry : outputMap.entries) {
                    if (Arrays.equals(patch.old1, outputEntry.getHash())) {
                        outputEntry.setHash(patch.new1);
                    }
                }
            }
            
            outputFarc.sortEntries();
            outputMap.sortEntries();
            rafDest.seek(rafDest.length());
            for (FarcEntry entry : outputFarc.entries) {
                rafDest.write(entry.getHash());
                rafDest.write(MiscUtils.longToByteArray(entry.getOffset()));
                rafDest.write(MiscUtils.longToByteArray(entry.getSize()));
            }
            rafDest.writeInt(outputFarc.entries.size());
            rafDest.write(new byte[]{0x46, 0x41, 0x52, 0x43});
            exportMapToFile(outputMap, mapOut);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            return false;
        }
        
    }
    
    public static boolean getLBP3AlphaRevNumbers(File farcSrc, File mapSrc) {
        FarcFile farcFileSrc = parseFarcFile(farcSrc);
        MapFile mapFileSrc = parseMapFile(mapSrc);
        try {
            int i=0; 
            for (MapEntry entry : mapFileSrc.entries) {
                if (entry.getPath().contains(".plan")) {
                    
                    //System.out.println("im on " + MiscUtils.byteArrayToHexString(entry.getHash()));
                    byte[] bytesToRead = pullFromFarc(entry.getHash(), farcFileSrc, mapFileSrc);
                    if (bytesToRead==null) {
                        System.out.println("Fuck");
                        continue;
                    }
                    ByteArrayInputStream fileAccess = new ByteArrayInputStream(bytesToRead);
                    fileAccess.skip(4);
                    byte[] revNumb = new byte[4];
                    fileAccess.read(revNumb);
                    long revisionLong = MiscUtils.byteArrayToLong(revNumb);
                    //if (revisionLong > MiscUtils.byteArrayToLong(MiscUtils.hexStringToByteArray("000003F8")) ) {
                    if (entry.getPath().contains(".plan")) {
                        //System.out.println(MiscUtils.byteArrayToHexString(MiscUtils.longToByteArray(revisionLong)) + " - " + entry.getPath());
                        //if (revisionLong <= MiscUtils.byteArrayToLong(MiscUtils.hexStringToByteArray("00CB03E7")) ) {
                            //if (entry.getPath().contains("cos_") || entry.getPath().contains("obj_") || entry.getPath().contains("env_")) {
                                //System.out.println("\t[" + (i) + "]");
                                //System.out.println("\t\tGUID.guid\t" + entry.getGUID());
                                //System.out.println("\t\tFlags 0");

                                System.out.println("\t\t\t[" + (i) + "]\t" + entry.getGUID());

                                //System.out.println(entry.getPath());
                                i++;
                            //}
                        //}
                        //System.out.println(MiscUtils.byteArrayToHexString(revNumb).toUpperCase() + " - " + entry.getPath() + " - " + MiscUtils.byteArrayToHexString(MiscUtils.longToByteArray(entry.getGUID())).toUpperCase());
                    }
                    fileAccess.close();
                }
            }

            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            return false;
        }
        
    }
    
    public static boolean port(File farcSrc, File mapSrc, File farcSrc2, File mapSrc2, String guid, File farcOut, File mapOut) {

        FarcFile farcFileSrc = parseFarcFile(farcSrc);
        
        MapFile mapFileSrc = parseMapFile(mapSrc);
        MapFile mapFileSrc2 = parseMapFile(mapSrc2);
 
        MapEntry toPort = null;
        for (MapEntry entry : mapFileSrc.entries) {
            if ((guid.length()==8 && entry.getGUID() == Long.parseLong(guid)) || Arrays.equals(entry.getHash(), MiscUtils.hexStringToByteArray(guid))) {
                toPort = entry;
                break;
            }
        }
        
        if (toPort==null) {
            System.out.println("toPort is null");
            return false;
        }
        
        MapFile deps = getDepedenciesFromEntry(toPort, farcFileSrc, mapFileSrc);
        deps.sortEntries();
        
        for (MapEntry entry : deps.entries) {
            boolean pass = true;
            for (MapEntry entryB : mapFileSrc2.entries) {
                if (entryB.getGUID() == entry.getGUID()) {
                    System.out.println("WARNING: entry " + entry.getPath() + " already found, skipping");   
                    pass = false;
                }
            }
            if (pass==true) {
                System.out.println("SUCCESS: adding " + entry.getPath() + " to map");  
                mapFileSrc2.addEntry(entry);
            }
        }
        mapFileSrc2.sortEntries();
        exportMapToFile(mapFileSrc2, mapOut);
       
        return true;
    }
    
    public static boolean extract(File farcSrc, File mapSrc, String guid, File farcOut, File mapOut) {

        FarcFile farcFileSrc = parseFarcFile(farcSrc);
        MapFile mapFileSrc = parseMapFile(mapSrc);
 
        MapEntry toPort = null;
        for (MapEntry entry : mapFileSrc.entries) {
            if ((guid.length()==8 && entry.getGUID() == Long.parseLong(guid)) || Arrays.equals(entry.getHash(), MiscUtils.hexStringToByteArray(guid))) {
                toPort = entry;
                break;
            }
        }
        
        if (toPort==null) {
            System.out.println("toPort is null");
            return false;
        }
        
        MapFile deps = getDepedenciesFromEntry(toPort, farcFileSrc, mapFileSrc);
        deps.sortEntries();
        
        MapFile mapFileSrc2 = new MapFile();
        
        for (MapEntry entry : deps.entries) {
            boolean pass = true;
            for (MapEntry entryB : mapFileSrc2.entries) {
                if (entryB.getGUID() == entry.getGUID()) {
                    System.out.println("WARNING: entry " + entry.getPath() + " already found, skipping");   
                    pass = false;
                }
            }
            if (pass==true) {
                System.out.println("SUCCESS: adding " + entry.getPath() + " to map");  
                mapFileSrc2.addEntry(entry);
            }
        }
        mapFileSrc2.sortEntries();
        exportMapToFile(mapFileSrc2, mapOut);
       
        return true;
    }
    
    public static MapFile parseMapFile(File fileHandle) {
        MapFile memoryMap = new MapFile();
        try {
            DataInputStream mapAccess = new DataInputStream(new FileInputStream(fileHandle));
            int header = mapAccess.readInt();
            memoryMap.setMapType(header);
            int entryCount = mapAccess.readInt();
            for (int i = 0; i < entryCount; i++) {
                if (header!=21496064) mapAccess.skip(2);
                short fileNameLength = mapAccess.readShort();
                byte[] fileNameBytes = new byte[fileNameLength];
                mapAccess.read(fileNameBytes);
                String fileName = new String(fileNameBytes);
                if (header!=21496064) mapAccess.skip(4);
                int timestamp = mapAccess.readInt();
                int fileSize = mapAccess.readInt();
                byte[] hash = new byte[20];
                mapAccess.read(hash);
                byte[] guidBytes = new byte[4];
                mapAccess.read(guidBytes);
                long guid = MiscUtils.byteArrayToLong(guidBytes); //prehistoric moves fix
                memoryMap.addEntry(new MapEntry(fileName, timestamp, fileSize, hash, guid));
            }
            mapAccess.close();
            return memoryMap;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public static FarcFile parseFarcFile(File fileHandle) {
        FarcFile memoryFarc = new FarcFile(fileHandle);
        try {
            RandomAccessFile farcAccess = new RandomAccessFile(memoryFarc.getFileHandle(), "rw");
            
            farcAccess.seek(farcAccess.length()-8);
            int entryCount = farcAccess.readInt();
            
            long tableOffset = farcAccess.length()-8-(entryCount*28);
            memoryFarc.setEntriesOffset(tableOffset);
            farcAccess.seek(tableOffset);
            
            for (int i = 0; i < entryCount; i++) {
                byte[] hash = new byte[20];
                farcAccess.read(hash);
                byte[] offsetBytes = new byte[4];
                farcAccess.read(offsetBytes);
                long offset = MiscUtils.byteArrayToLong(offsetBytes);
                byte[] sizeBytes = new byte[4];
                farcAccess.read(sizeBytes);
                long size = MiscUtils.byteArrayToLong(sizeBytes);
                FarcEntry entry = new FarcEntry(hash, offset, size);
                memoryFarc.addEntry(entry);
            }
            farcAccess.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return memoryFarc;
    }
   
    public static void exportMapToFile(MapFile mapSource, File fileDest) {
        
        if (fileDest.exists()) {
            if (!fileDest.delete()) {
                System.out.println("error deleting file");
                return;
            }
        }
        
        try {
            fileDest.createNewFile();
            DataOutputStream mapAccess = new DataOutputStream(new FileOutputStream(fileDest));
            mapAccess.writeInt(mapSource.getMapType());
            mapAccess.writeInt(mapSource.getMapEntryCount());
            for (MapEntry mapEntry : mapSource.entries) {
                if (mapSource.getMapType()!=21496064) mapAccess.write(new byte[2]);
                mapAccess.writeUTF(mapEntry.getPath());
                if (mapSource.getMapType()!=21496064) mapAccess.write(new byte[4]);
                mapAccess.writeInt(mapEntry.getTimestamp());
                mapAccess.writeInt(mapEntry.getSize());
                mapAccess.write(mapEntry.getHash());
                mapAccess.writeInt((int) mapEntry.getGUID());
            }
            mapAccess.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static byte[] pullFromFarc(byte[] bytesSHA1, FarcFile farcIndex, MapFile mapSource) {
        
        if (farcIndex.getFileHandle() == null) return null;

        long entryOffset = 0;
        long entrySize = 0;
        
        for (FarcEntry entry : farcIndex.entries) {
            if (Arrays.equals(entry.getHash(), bytesSHA1)) {
                entryOffset = entry.getOffset();
                entrySize = entry.getSize();
            }
        }
        
        if (entryOffset == 0) {
            for (MapEntry mapEntry : mapSource.entries) {
                if (Arrays.equals(mapEntry.getHash(), bytesSHA1)) {
                    System.out.println("warning: " + mapEntry.getPath() + " not found within farc, but exists in map");
                    return null;
                }
            }
            System.out.println("sha1 " + MiscUtils.byteArrayToHexString(bytesSHA1) + " not in farc or map");
            return null;
        }
        
        try {
            
            FileInputStream fin = new FileInputStream(farcIndex.getFileHandle());
            fin.skip(entryOffset);
            byte[] outputbytes = new byte[(int)entrySize];
            fin.read(outputbytes);
            fin.close();

            return outputbytes;
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    
    
    public static MapFile getDepedenciesFromEntry(MapEntry srcEntry, FarcFile srcFarc, MapFile srcMap) {
        MapFile output = new MapFile();
        try {
            byte[] bytesToRead = pullFromFarc(srcEntry.getHash(), srcFarc, srcMap);
            ByteArrayInputStream fileAccess = new ByteArrayInputStream(bytesToRead);
            fileAccess.skip(8);
            //Get dependencies offset
            byte[] offsetDependenciesByte = new byte[4];
            fileAccess.read(offsetDependenciesByte);
            int offsetDependencies = Integer.parseInt(MiscUtils.byteArrayToHexString(offsetDependenciesByte), 16);
            
            fileAccess.skip(offsetDependencies-12);
            
            byte[] dependenciesCountByte = new byte[4];
            fileAccess.read(dependenciesCountByte);
            int dependenciesCount = Integer.parseInt(MiscUtils.byteArrayToHexString(dependenciesCountByte), 16);
            
            byte[] dependencyKindByte = new byte[1];
            byte[] dependencyGUIDByte = new byte[4];
            long dependencyGUID = 0;

            boolean levelFail = false;
            
            for (int i=0; i<dependenciesCount; i++ ) {
                fileAccess.read(dependencyKindByte);
                if (dependencyKindByte[0]==0x01) {
                    fileAccess.skip(20); //sha1
                    fileAccess.skip(4);
                }
                if (dependencyKindByte[0]==0x02) {
                    fileAccess.read(dependencyGUIDByte);
                    dependencyGUID = MiscUtils.byteArrayToLong(dependencyGUIDByte);
                    fileAccess.skip(4);
                    String fileNameNew = "Error";
                    byte[] sha1New = new byte[20];
                    for (MapEntry mapEntry : srcMap.entries) {
                        if (mapEntry.getGUID() == MiscUtils.byteArrayToLong(dependencyGUIDByte)) {
                            fileNameNew = mapEntry.getPath();
                            sha1New = mapEntry.getHash();
                            output.addEntry(mapEntry);
                            output.entries.addAll(getDepedenciesFromEntry(mapEntry, srcFarc, srcMap).entries);
                        }
                    }
                    if (fileNameNew.contains("Error")) {
                        levelFail=true;
                    }
                    
                    //System.out.println(fileNameNew + " | " + "g" + dependencyGUID + " | " + MiscUtils.byteArrayToHexString(sha1New));
                }
                
            }
            if (levelFail==true) {
                System.out.println("ERROR! this contains at least one dependency that does not exist. You will have problems.");
            }
            
        } catch (IOException ex) {
        } catch (NullPointerException ex) {
        }
        return output;
    }
    
    
    
}
