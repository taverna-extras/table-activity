package net.sf.taverna.t2.activities.table.utils;

/**
 * Format of an input table.
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/inFormats.html
 * @author christian
 */
public enum TableInputFormat  implements DescribableInterface {
     CSV("csv", "Comma-Separated Values format", 
             "Comma-Separated Values format, using approximately the conventions used by MS Excel."),
     TST("tst", "Tab-Separated Table format",
             "Tab-Separated Table format, as used by Starlink's GAIA and ESO's SkyCat amongst other tools."),
     ASCII("ascii", "Whitespace seperated plain text", 
             "Plain text file with one row per column in which columns are separated by whitespace."),

     FITS("fits", "FITS format",
             "FITS format - FITS binary or ASCII tables can be read.\n"
             + "For commands which take a single input table, by default the first table HDU in the file will used,\n"
             + "but this can be altered for multi-extension FITS files by supplying an identifier after a '#' sign.\n"
             + "The identifier can be either an HDU index or the extension name (EXTNAME header,\n"
             + "possibly followed by \"-\" and the EXTVER header), so \"table.fits#3\" means the third HDU extension,\n"
             + "and \"table.fits#UV_DATA\" means the HDU with the value \"UV_DATA\" for its EXTNAME header card."),
     COLFITS("colfits", "FITS format (Column-oriented )",
             "Column-oriented FITS format.\n"
             + "This is where a table is stored as a BINTABLE extension which contains a single row,\n"
             + "each cell of the row containing a whole column of the table it represents.\n"
             + "This has different performance characteristics from normal FITS tables;\n"
             + "in particular it may be considerably more efficient for very large,\n"
             + "and especially very wide tables where not all of the columns are required at any one time.\n"
             + "Only available for uncompressed files on disk."),
     IPAC("ipac", "IPAC Table Format.", "IPAC Table Format."),
     VOTABLE("votable", "VOTable format",
             "VOTable format - any legal version 1.0, 1.1 or 1.2 format VOTable documents,\n"
             + "and many illegal ones, can be read. For commands which take a single input table,\n"
             + "by default the first TABLE element in the document is used,\n"
             + "but this can be altered by supplying the 0-based index after a '#' sign,\n"
             + "so \"table.xml#4\" means the fifth TABLE element in the document. "),
     //votable 1.3 only added in Stilts 2.5
     ///VOTABLE("votable", "VOTable format - any legal version 1.0, 1.1, 1.2 or 1.3 format VOTable documents, "
     //        + "and many illegal ones, can be read. For commands which take a single input table, "
     //        + "by default the first TABLE element in the document is used, "
     //        + "but this can be altered by supplying the 0-based index after a '#' sign, "
     //        + "so \"table.xml#4\" means the fifth TABLE element in the document. "),
     //Ony added in 2.5.1
     //CDF("cdf", "NASA Common Data Format. CDF is described at http://cdf.gsfc.nasa.gov/."),
     WDC("wdc", "World Datacentre Format", "World Datacentre Format (experimental). ");

    private final String stiltsName;
    private final String userName;
    private final String description;

    
    TableInputFormat(String stiltsName, String userName, String description){
        this.stiltsName = stiltsName;  
        this.userName = userName;
        this.description = description;
    }

    /**
     * @return the stiltsName
     */
    public String getStiltsName() {
        return stiltsName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /*public static TableInputFormat byStiltsName(String stiltsName){
        for (TableInputFormat format:TableInputFormat.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No TableInputFormat known for " + stiltsName);
    }*/

    @Override
    public String toString(){
        return userName;
    }
}
