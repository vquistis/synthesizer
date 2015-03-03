<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module4</idModule>
            <name>eg_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module5</idModule>
            <name>rep_out2</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0xcc3333ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module3</idModule>
            <name>vca_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>vcf_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module3</idModule>
            <name>vca_inputam</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module4</idModule>
            <name>eg_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0xcc3333ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>vcf_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module2</idModule>
            <name>outputAmplitude</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0xcc3333ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module6</idModule>
            <name>out_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module3</idModule>
            <name>vca_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>vcf_fm</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module5</idModule>
            <name>rep_out1</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0xffff4dff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module5</idModule>
            <name>rep_in</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module0</idModule>
            <name>outputAmplitude</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <modules>
        <filename>fxml/vco.fxml</filename>
        <id>Module0</id>
        <parameters>
            <entry>
                <key>octave</key>
                <value>4.0</value>
            </entry>
            <entry>
                <key>typeOutput</key>
                <value>2.0</value>
            </entry>
            <entry>
                <key>precision</key>
                <value>0.49181244426520343</value>
            </entry>
            <entry>
                <key>choiceBaseFreq</key>
                <value>0.0</value>
            </entry>
            <entry>
                <key>choiceAmplitude</key>
                <value>1.0</value>
            </entry>
        </parameters>
        <ports>
            <connected>false</connected>
            <name>vco_inputFm</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputf0</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputOctave</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputSelectShape</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputAmplitude</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>outputAmplitude</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputSquare</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputSawTooth</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputTriangle</name>
            <type>OUT</type>
        </ports>
        <posX>0</posX>
        <posY>0</posY>
    </modules>
    <modules>
        <filename>fxml/vcf-lp.fxml</filename>
        <id>Module1</id>
        <parameters>
            <entry>
                <key>knobCutoff</key>
                <value>2087.200075935754</value>
            </entry>
            <entry>
                <key>knobResonance</key>
                <value>1.0435148148964972</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>vcf_input</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>vcf_fm</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>vcf_output</name>
            <type>OUT</type>
        </ports>
        <posX>0</posX>
        <posY>1</posY>
    </modules>
    <modules>
        <filename>fxml/vco.fxml</filename>
        <id>Module2</id>
        <parameters>
            <entry>
                <key>octave</key>
                <value>5.0</value>
            </entry>
            <entry>
                <key>typeOutput</key>
                <value>0.0</value>
            </entry>
            <entry>
                <key>precision</key>
                <value>0.0</value>
            </entry>
            <entry>
                <key>choiceBaseFreq</key>
                <value>2.0</value>
            </entry>
            <entry>
                <key>choiceAmplitude</key>
                <value>1.0</value>
            </entry>
        </parameters>
        <ports>
            <connected>false</connected>
            <name>vco_inputFm</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputf0</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputOctave</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputSelectShape</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>inputAmplitude</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>outputAmplitude</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputSquare</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputSawTooth</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vco_outputTriangle</name>
            <type>OUT</type>
        </ports>
        <posX>1</posX>
        <posY>0</posY>
    </modules>
    <modules>
        <filename>fxml/vca.fxml</filename>
        <id>Module3</id>
        <parameters>
            <entry>
                <key>amplitudeKnod</key>
                <value>3.0769112503605722</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>vca_input</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>vca_inputam</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vca_inputa0</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>vca_output</name>
            <type>OUT</type>
        </ports>
        <posX>1</posX>
        <posY>1</posY>
    </modules>
    <modules>
        <filename>fxml/eg.fxml</filename>
        <id>Module4</id>
        <parameters>
            <entry>
                <key>knobDecay</key>
                <value>0.33173931602810525</value>
            </entry>
            <entry>
                <key>knobSustain</key>
                <value>-2.6789290274577198</value>
            </entry>
            <entry>
                <key>knobAttack</key>
                <value>0.3524775959964107</value>
            </entry>
            <entry>
                <key>knobRelease</key>
                <value>0.340002408201117</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>eg_input</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>eg_output</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>Attack</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>Decay</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>Sustain</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>Release</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>Amplitude</name>
            <type>IN</type>
        </ports>
        <posX>2</posX>
        <posY>1</posY>
    </modules>
    <modules>
        <filename>fxml/rep.fxml</filename>
        <id>Module5</id>
        <parameters/>
        <ports>
            <connected>true</connected>
            <name>rep_in</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>rep_out1</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>rep_out2</name>
            <type>OUT</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>rep_out3</name>
            <type>OUT</type>
        </ports>
        <posX>2</posX>
        <posY>0</posY>
    </modules>
    <modules>
        <filename>fxml/out.fxml</filename>
        <id>Module6</id>
        <parameters>
            <entry>
                <key>muteVolumeFx</key>
                <value>0.0</value>
            </entry>
            <entry>
                <key>knobVolume</key>
                <value>-10.470417261397081</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>out_input</name>
            <type>IN</type>
        </ports>
        <posX>3</posX>
        <posY>1</posY>
    </modules>
</configuration>
