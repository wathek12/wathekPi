package com.wathek.wathek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationStatsResponse implements Serializable {

    private long totale;
    private long traiter;
    private long nonTraiter;

    private long lu;
    private long nonLu;
}
