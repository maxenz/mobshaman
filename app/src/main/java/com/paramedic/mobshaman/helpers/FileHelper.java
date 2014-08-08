package com.paramedic.mobshaman.helpers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by maxo on 07/08/14.
 */
public final class FileHelper {

    public static ArrayList<String> readFileInternalStorage(String fileName, Context context)
    {
        //String stringToReturn = " ";
        ArrayList<String> inputsFromFile = new ArrayList<String>();
        try
        {
            String sfilename = fileName;
            InputStream inputStream = context.openFileInput(sfilename);


            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                //StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    inputsFromFile.add(receiveString);
                    //stringBuilder.append(receiveString);
                }
                inputStream.close();

                //stringToReturn = stringBuilder.toString();
            }

        }
        catch (FileNotFoundException e)
        {
            Log.e("TAG", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("TAG", "Can not read file: " + e.toString());
        }

        return inputsFromFile;
    }

}
