<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<configuration>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module0</idModule>
            <name>vco_inputFm</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>rep_out3</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module7</idModule>
            <name>echo_in</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module6</idModule>
            <name>vcf_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module4</idModule>
            <name>vca_input</name>
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
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module4</idModule>
            <name>vca_inputam</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module3</idModule>
            <name>eg_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
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
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module2</idModule>
            <name>vco_inputFm</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>rep_out1</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module3</idModule>
            <name>eg_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module1</idModule>
            <name>rep_out2</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module5</idModule>
            <name>out_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module7</idModule>
            <name>echo_out</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <connections>
        <color>0x8a2be2ff</color>
        <inputPort>
            <connected>true</connected>
            <idModule>Module6</idModule>
            <name>vcf_input</name>
            <type>IN</type>
        </inputPort>
        <outputPort>
            <connected>true</connected>
            <idModule>Module4</idModule>
            <name>vca_output</name>
            <type>OUT</type>
        </outputPort>
    </connections>
    <modules>
        <filename>fxml/vco.fxml</filename>
        <id>Module0</id>
        <parameters>
            <entry>
                <key>octave</key>
                <value>2.0</value>
            </entry>
            <entry>
                <key>typeOutput</key>
                <value>2.0</value>
            </entry>
            <entry>
                <key>precision</key>
                <value>0.0</value>
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
            <connected>true</connected>
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
        <filename>fxml/rep.fxml</filename>
        <id>Module1</id>
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
            <connected>true</connected>
            <name>rep_out3</name>
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
                <value>0.0</value>
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
                <value>3.0</value>
            </entry>
            <entry>
                <key>choiceAmplitude</key>
                <value>1.0</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
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
        <filename>fxml/eg.fxml</filename>
        <id>Module3</id>
        <parameters>
            <entry>
                <key>knobDecay</key>
                <value>0.15327266324178143</value>
            </entry>
            <entry>
                <key>knobSustain</key>
                <value>-2.6138512993982914</value>
            </entry>
            <entry>
                <key>knobAttack</key>
                <value>0.17893629440596168</value>
            </entry>
            <entry>
                <key>knobRelease</key>
                <value>1.4859836568331406</value>
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
        <posX>1</posX>
        <posY>1</posY>
    </modules>
    <modules>
        <filename>fxml/vca.fxml</filename>
        <id>Module4</id>
        <parameters>
            <entry>
                <key>amplitudeKnod</key>
                <value>1.2004348790002464</value>
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
        <posX>2</posX>
        <posY>1</posY>
    </modules>
    <modules>
        <filename>fxml/out.fxml</filename>
        <id>Module5</id>
        <parameters>
            <entry>
                <key>muteVolumeFx</key>
                <value>1.0</value>
            </entry>
            <entry>
                <key>knobVolume</key>
                <value>-0.10739801714500885</value>
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
    <modules>
        <filename>fxml/vcf-lp.fxml</filename>
        <id>Module6</id>
        <parameters>
            <entry>
                <key>knobCutoff</key>
                <value>796.5626618158399</value>
            </entry>
            <entry>
                <key>knobResonance</key>
                <value>1.0</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>vcf_input</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>false</connected>
            <name>vcf_fm</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>vcf_output</name>
            <type>OUT</type>
        </ports>
        <posX>2</posX>
        <posY>0</posY>
    </modules>
    <modules>
        <filename>fxml/echo.fxml</filename>
        <id>Module7</id>
        <parameters>
            <entry>
                <key>knobEchoPeriod</key>
                <value>0.2902532206339826</value>
            </entry>
            <entry>
                <key>knobEchoAttenuation</key>
                <value>-3.5241774288184757</value>
            </entry>
        </parameters>
        <ports>
            <connected>true</connected>
            <name>echo_in</name>
            <type>IN</type>
        </ports>
        <ports>
            <connected>true</connected>
            <name>echo_out</name>
            <type>OUT</type>
        </ports>
        <posX>3</posX>
        <posY>0</posY>
    </modules>
</configuration>
