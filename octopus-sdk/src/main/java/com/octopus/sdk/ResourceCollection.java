package com.octopus.sdk;

import java.util.Map;

public class ResourceCollection<TResource> {
    public TResource[] Items;
    public int ItemsPerPage;
    public int LastPageNumber;
    public int NumberOfPages;
    public int TotalResults;
}
