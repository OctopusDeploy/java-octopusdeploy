package com.octopus.sdk;

public class SpaceNameCollection {
    public String spaceName;
    public String[] ids;
    public int skip;
    public int take;

     public SpaceNameCollection(String spaceName, String[] ids, int skip, int take) {
         this.spaceName = spaceName;
         this.ids = ids;
         this.skip = skip;
         this.take = take;
     }
}
