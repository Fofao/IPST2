/* ********************************************************************************************** *
 * ********************************************************************************************** *
 *                                                                                                *
 * Copyright 2017 Steven Foskett, Jimmy Ho, Ryan Porterfield                                      *
 *                                                                                                *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  *
 * and associated documentation files (the "Software"), to deal in the Software without           *
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,     *
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the  *
 * Software is furnished to do so, subject to the following conditions:                           *
 *                                                                                                *
 * The above copyright notice and this permission notice shall be included in all copies or       *
 * substantial portions of the Software.                                                          *
 *                                                                                                *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING  *
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND     *
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,   *
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, *
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.        *
 *                                                                                                *
 * ********************************************************************************************** *
 * ********************************************************************************************** */

package com.einzig.ipst2.parse;

import com.einzig.ipst2.database.DatabaseInterface;
import com.einzig.ipst2.portal.PortalSubmission;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ryan Porterfield
 * @since 2017-05-24
 */

abstract class PortalBuilder<P extends PortalSubmission> {
    final private DatabaseInterface db;

    PortalBuilder(DatabaseInterface db) {
        this.db = db;
    }

    /**
     * Create a new portal
     *
     * @param name The portal name.
     * @param dateResponded The date the portal was rejected.
     * @param message The body of the email as a String for parsing.
     */
    abstract P build(String name, Date dateResponded, String message);

    PortalSubmission findPortal(String pictureURL) {
        return db.getPendingPortal(pictureURL);
    }

    /**
     * Parse the URL of the portal picture from the email.
     * @param message The body of the email as a String for parsing.
     * @return the URL of the portal picture.
     */
    String parsePictureURL(String message) {
        String pictureURL;
        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>",
                Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find())
            pictureURL = matcher.group(1);
        else
            pictureURL = "No Picture Found";
        return pictureURL;
    }
}