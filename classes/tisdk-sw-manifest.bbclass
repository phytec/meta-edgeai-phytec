# Manifest file location which will be created as part of the image build
# process.
# This manifest follows the TI manifest format requirements which is why
# it differs from some of the default manifest code in oe-core.
SW_MANIFEST_FILE ?= "${IMAGE_ROOTFS}/manifest/software_manifest.htm"
SW_MANIFEST_TEXT ?= "${IMAGE_ROOTFS}/manifest/software_manifest.txt"

SW_MANIFEST_TOOLCHAIN_DIR ?= "${SDKDEPLOYDIR}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}-sw_manifest"
SW_MANIFEST_TOOLCHAIN_HOST_FILE ?= "${SW_MANIFEST_TOOLCHAIN_DIR}/host.htm"
SW_MANIFEST_TOOLCHAIN_HOST_TEXT ?= "${SW_MANIFEST_TOOLCHAIN_DIR}/host.txt"
SW_MANIFEST_TOOLCHAIN_TARGET_FILE ?= "${SW_MANIFEST_TOOLCHAIN_DIR}/target.htm"
SW_MANIFEST_TOOLCHAIN_TARGET_TEXT ?= "${SW_MANIFEST_TOOLCHAIN_DIR}/target.txt"

SW_MANIFEST_TOOLCHAIN_DEPLOY_DIR ?= "${SDK_DEPLOY}/${SDK_NAME}-${ARMPKGARCH}-${TARGET_OS}${TOOLCHAIN_SUFFIX}-sw_manifest"
SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_FILE ?= "${SW_MANIFEST_TOOLCHAIN_DEPLOY_DIR}/host.htm"
SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_TEXT ?= "${SW_MANIFEST_TOOLCHAIN_DEPLOY_DIR}/host.txt"
SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_FILE ?= "${SW_MANIFEST_TOOLCHAIN_DEPLOY_DIR}/target.htm"
SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_TEXT ?= "${SW_MANIFEST_TOOLCHAIN_DEPLOY_DIR}/target.txt"

# Generate the header for a TI style software manifest
sw_manifest_header() {
echo "
<HTML>
<HEAD>
<TITLE>
${MACHINE} TISDK ${SDK_VERSION} Installation Summary
</TITLE>
</HEAD>
<BODY>
<h1><CENTER> ${MACHINE} TISDK ${SDK_VERSION} Software Manifest </CENTER></h1>
<h2><CENTER> `date +"%B %d, %Y"` </CENTER></h2>
<h2><b><u>Legend (explanation of the fields in the Manifest Table below)</u></b></h2>
<table border=1 width=45%>
<tr>
    <td>Software Name</td>
    <td>The name of the application or files</td>
</tr>
<tr>
    <td>Version</td>
    <td>Version of the application or files</td>
</tr>
<tr>
    <td>License Type</td>
    <td>Type of license(s) under which TI will be providing software to the licensee (e.g. BSD, GPLv2, TI TSPA License, TI Commercial License).  See Open Source Reference License Disclaimer in the Disclaimers Section.</td>
</tr>
<tr>
    <td>Location</td>
    <td>The directory name and path on the media (or in an archive) where the Package is located.</td>
</tr>
<tr>
    <td>Delivered As</td>
    <td>This field will either be &ldquo;Source&rdquo;, &ldquo;Binary&rdquo; or &ldquo;Source and Binary&ldquo; and is the form the content of the Package is delivered in.  If the Package is delivered in an archive format, this field applies to the contents of the archive. If the word Limited is used with Source, as in &ldquo;Limited Source&rdquo; or &ldquo;Limited Source and Binary&rdquo; then only portions of the Source for the application are provided.</td>
</tr>
<tr>
    <td>Modified by TI</td>
    <td>This field will either be &ldquo;Yes&rdquo; or &ldquo;No&rdquo;. A &ldquo;Yes&rdquo; means TI had made changes to the Software. A &ldquo;No&rdquo; means TI has not made any changes.  NOTE: This field is not applicable for Software &ldquo;Obtained from&rdquo; TI.</td>
</tr>
<tr>
    <td>Obtained from</td>
    <td>This field specifies from where or from whom TI obtained the Software. It may be a URL to an Open Source site, a 3rd party companylicensor, or TI (if TI developed the software). If this field contains a link to an Open Source software, the date it was downloaded is also recorded.  See Links Disclaimer in the Disclaimers Section.</td>
</tr>
</table>


<h2><b>DISCLAIMERS</b></h2>
<h2><u>Export Control Classification Number (ECCN)</u></h2>
<p>
Any use of ECCNs listed in the Manifest is at the user's risk and without recourse to TI.  Your company, as the exporter of record, is responsible for determining the correct classification of any item at the time of export.  Any export classification by TI of Software is for TI's internal use only and shall not be construed as a representation or warranty regarding the proper export classification for such Software or whether an export license or other documentation is required for exporting such Software.
</p>

<h2><u>Links in the Manifest</u></h2>
<p>
Any links appearing on this Manifest (for example in the &ldquo;Obtained from&rdquo; field) were verified at the time the Manifest was created.  TI makes no guarantee that any listed links will remain active in the future.
</p>

<h2><u>Open Source License References</u></h2>
<p>
Your company is responsible for confirming the applicable license terms for any open source Software listed in this Manifest that was not &ldquo;Obtained from&rdquo; TI.  Any open source license specified in this Manifest for Sotware that was not &ldquo;Obtained from&rdquo; TI is for TI's internal use only and shall not be construed as a representation of warranty regarding the proper open source license terms for such Software.
</p>

<h2><b>Export Information</b></h2>
<p>
ECCN for Software included in this release: Publicly Available
</p>

<p>
ECCN for Technology (e.g., user documentation, specifications) included in this release: Publicly Available
</p>

<h2><b>Manifest</b></h2>
<p>
See Legend above for a description of the columns and possible values.
</p>
" > ${SW_MANIFEST_FILE}

cat > ${SW_MANIFEST_TEXT} << 'EOF'
<--
Manifest template 1.0

This template is used to generate an unloadable manifest to the SRAS. To do so, save the document as a plain text file (Save As .txt). Do not change any options on the save which means leave the default as Windows Default Encoding. The SRAS will generate a nice looking HTML manifest for you.

Instructions:

 1.   Text in blue should not be edited or removed. It will NOT appear on your manifest.
 2.   DO NOT color outside the lines in the manifest.  There are specific blocks where you enter information. Information
       should only be entered in the sections below and not outside of them.
 3.   DO NOT use the < or > symbols in your manifest table or in your footnotes.
 4.   Please REFRAIN from using Word specific formatting when filling in the table. Examples are things like superscripts,
      subscripts, etc. We are unable to understand those.
 5.   Auto correction is not your friend in word. Please turn it off. It should be disabled already in this template.

Table Instructions:

Define what you are delivering by filling in the table below. Tables start with the start keyword <table> end with the end keyword, </table> and may also contain footnotes specific to that table.

You can have as many .Public. tables as you like in your manifest and can name them.  You can have one and only one .Private. table. To create additional tables copy everything starting with <table> down to </table> and then paste it after the table below.

The field .attribute =. can be set to Public (the default) or Private to define the type of table it is. If the table is Public then everything in the table will appear on your manifest. If the table is Private then none of the entries will appear on your manifest but they will be stored in the SRAS. Private tables are useful for documenting third party code, that requires Legal review, but does not need to be acknowledged on the manifest you ship; e.g. it may be covered by our TI license.

The field .name = . is used to put a heading over the table on the manifest you ship.

The field .description =. is used to put a description under the Table name on the manifest you ship.

To learn how to document software in the table read the instructions here. A brief explanation of the table columns follows.
License Type - If its an SPDX defined license use their short name identifier. You can see a list here: http://spdx.org/licenses/ . If it.s not on the list and not a commercial or TSPA  license then use the application name as the license.

Delivered As - This field will either be .Source., .Binary. or .Source and Binary. and is the form the content of the Software is delivered in.  If the Software is delivered in an archive format, this field applies to the contents of the archive. If the word Limited is used with Source, as in .Limited Source. or .Limited Source and Binary. then only portions of the Source for the application are provided.

Modified by TI - This field will either be .Yes. or .No.. A .Yes. means TI has made changes to the Software. A .No. means TI has not made any changes. Note: This field is not applicable for Software .Obtained from. TI.

Location - The directory name and path on the media, e.g. drive, (or in an archive) where the Software is located after installing or extracting.

Obtained From - This field specifies from where or from whom TI obtained the Software. It may be a URL to an Open Source site, a 3rd party licensor, or TI (if TI developed the software). If this field contains a link to Open Source software, the date TI downloaded the Software is also recorded. See Links Disclaimer in the Disclaimers Section.
-->

<directives>
Version = .1.0.
Sep = .|.
Tool=.Word.
</directives>

<-- ** Do not edit or remove anything above this line **
-->

<--
Instructions: DO NOT Alter the column headings below. This is now a single row table as opposed to the older manifests which used merged rows. The order is extremely important; if you change the order or add or remove a column the upload will not work.
-->

EOF

}

sw_manifest_footer() {
echo "
<h2>Credits</h2>

<h2>Licenses</h2>
<p>Licenses can be found in the \"licenses\" directory of the SDK install directory</p>

<h2><u><b>GCC RUNTIME LIBRARY EXCEPTION</b></u></h2>
<p>Version 3.1, 31 March 2009</p>

<p>Copyright © 2009 Free Software Foundation, Inc. <a href=\"http://fsf.org/\">http://fsf.org/</a></p>

<p>Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.</p>

<p>This GCC Runtime Library Exception ("Exception") is an additional permission under section 7 of the GNU General Public License, version 3 ("GPL-3.0"). It applies to a given file (the "Runtime Library") that bears a notice placed by the copyright holder of the file stating that the file is governed by GPL-3.0 along with this Exception.</p>

<p>When you use GCC to compile a program, GCC may combine portions of certain GCC header files and runtime libraries with the compiled program. The purpose of this Exception is to allow compilation of non-GPL (including proprietary) programs to use, in this way, the header files and runtime libraries covered by this Exception.</p>

<h3><b>0. Definitions.</b></h3>
<p>A file is an "Independent Module" if it either requires the Runtime Library for execution after a Compilation Process, or makes use of an interface provided by the Runtime Library, but is not otherwise based on the Runtime Library.</p>

<p>"GCC" means a version of the GNU Compiler Collection, with or without modifications, governed by version 3 (or a specified later version) of the GNU General Public License (GPL) with the option of using any subsequent versions published by the FSF.</p>

<p>"GPL-compatible Software" is software whose conditions of propagation, modification and use would permit combination with GCC in accord with the license of GCC.</p>

<p>"GPL-compatible Software" is software whose conditions of propagation, modification and use would permit combination with GCC in accord with the license of GCC.</p>

<p>The "Compilation Process" transforms code entirely represented in non-intermediate languages designed for human-written code, and/or in Java Virtual Machine byte code, into Target Code. Thus, for example, use of source code generators and preprocessors need not be considered part of the Compilation Process, since the Compilation Process can be understood as starting with the output of the generators or preprocessors.</p>

<p>A Compilation Process is "Eligible" if it is done using GCC, alone or with other GPL-compatible software, or if it is done without using any work based on GCC. For example, using non-GPL-compatible Software to optimize any GCC intermediate representations would not qualify as an Eligible Compilation Process.</p>

<h3><b>1. Grant of Additional Permission.</b></h3>
<p>You have permission to propagate a work of Target Code formed by combining the Runtime Library with Independent Modules, even if such propagation would otherwise violate the terms of GPL-3.0, provided that all Target Code was generated by Eligible Compilation Processes. You may then convey such a combination under terms of your choice, consistent with the licensing of the Independent Modules.</p>

<h3><b>2. No Weakening of GCC Copyleft.</b></h3>
<p>The availability of this Exception does not imply any general presumption that third-party software is unaffected by the copyleft requirements of the license of GCC.</p>

<hr>

<h2>TEXAS INSTRUMENTS TEXT FILE LICENSE</h2>


<h3>Copyright (c) 2022 - 2023 Texas Instruments Incorporated</h3>

<p>All rights reserved not granted herein.</p>

<h3>Limited License.</h3>

<p>Texas Instruments Incorporated grants a world-wide, royalty-free, non-exclusive license under copyrights and patents it now or hereafter owns or controls to make, have made, use, import, offer to sell and sell ("Utilize") this software subject to the terms herein.  With respect to the foregoing patent license, such license is granted  solely to the extent that any such patent is necessary to Utilize the software alone.  The patent license shall not apply to any combinations which include this software, other than combinations with devices manufactured by or for TI (“TI Devices”).  No hardware patent is licensed hereunder.</p>

<p>Redistributions must preserve existing copyright notices and reproduce this license (including the above copyright notice and the disclaimer and (if applicable) source code license limitations below) in the documentation and/or other materials provided with the distribution</p>

<p>Redistribution and use in binary form, without modification, are permitted provided that the following conditions are met:</p>

<p>*	No reverse engineering, decompilation, or disassembly of this software is permitted with respect to any software provided in binary form.</p>

<p>*	any redistribution and use are licensed by TI for use only with TI Devices.</p>

<p>*	Nothing shall obligate TI to provide you with source code for the software licensed and provided to you in object code.</p>

<p>If software source code is provided to you, modification and redistribution of the source code are permitted provided that the following conditions are met:</p>

<p>*	any redistribution and use of the source code, including any resulting derivative works, are licensed by TI for use only with TI Devices.</p>

<p>*	any redistribution and use of any object code compiled from the source code and any resulting derivative works, are licensed by TI for use only with TI Devices.</p>

<p>Neither the name of Texas Instruments Incorporated nor the names of its suppliers may be used to endorse or promote products derived from this software without specific prior written permission.</p>

<h3>DISCLAIMER.</h3>

<p>THIS SOFTWARE IS PROVIDED BY TI AND TI’S LICENSORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL TI AND TI’S LICENSORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.</p>

<hr>

<right> <i><font color=grey>Auto generated by TISDK installer on `date`</i> </right></font>
</body>
</html>

" >> ${SW_MANIFEST_FILE}


cat >> ${SW_MANIFEST_TEXT} << 'EOF'
<Credits>

</credits>

<Licenses>

Licenses can be found in the "licenses/" directory of the SDK install directory
GCC RUNTIME LIBRARY EXCEPTION

Version 3.1, 31 March 2009

Copyright © 2009 Free Software Foundation, Inc. http://fsf.org/

Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.

This GCC Runtime Library Exception (Exception) is an additional permission under section 7 of the GNU General Public License, version 3 (GPL-3.0). It applies to a given file (the Runtime Library) that bears a notice placed by the copyright holder of the file stating that the file is governed by GPL-3.0 along with this Exception.

When you use GCC to compile a program, GCC may combine portions of certain GCC header files and runtime libraries with the compiled program. The purpose of this Exception is to allow compilation of non-GPL (including proprietary) programs to use, in this way, the header files and runtime libraries covered by this Exception.
0. Definitions.

A file is an Independent Module if it either requires the Runtime Library for execution after a Compilation Process, or makes use of an interface provided by the Runtime Library, but is not otherwise based on the Runtime Library.

GCC means a version of the GNU Compiler Collection, with or without modifications, governed by version 3 (or a specified later version) of the GNU General Public License (GPL) with the option of using any subsequent versions published by the FSF.

GPL-compatible Software is software whose conditions of propagation, modification and use would permit combination with GCC in accord with the license of GCC.

GPL-compatible Software is software whose conditions of propagation, modification and use would permit combination with GCC in accord with the license of GCC.

The Compilation Process transforms code entirely represented in non-intermediate languages designed for human-written code, and/or in Java Virtual Machine byte code, into Target Code. Thus, for example, use of source code generators and preprocessors need not be considered part of the Compilation Process, since the Compilation Process can be understood as starting with the output of the generators or preprocessors.

A Compilation Process is Eligible if it is done using GCC, alone or with other GPL-compatible software, or if it is done without using any work based on GCC. For example, using non-GPL-compatible Software to optimize any GCC intermediate representations would not qualify as an Eligible Compilation Process.
1. Grant of Additional Permission.

You have permission to propagate a work of Target Code formed by combining the Runtime Library with Independent Modules, even if such propagation would otherwise violate the terms of GPL-3.0, provided that all Target Code was generated by Eligible Compilation Processes. You may then convey such a combination under terms of your choice, consistent with the licensing of the Independent Modules.
2. No Weakening of GCC Copyleft.

The availability of this Exception does not imply any general presumption that third-party software is unaffected by the copyleft requirements of the license of GCC.

TEXAS INSTRUMENTS TEXT FILE LICENSE


Copyright (c) 2022 – 2023 Texas Instruments Incorporated

All rights reserved not granted herein.

Limited License.

Texas Instruments Incorporated grants a world-wide, royalty-free, non-exclusive license under copyrights and patents it now or hereafter owns or controls to make, have made, use, import, offer to sell and sell ("Utilize") this software subject to the terms herein.  With respect to the foregoing patent license, such license is granted  solely to the extent that any such patent is necessary to Utilize the software alone.  The patent license shall not apply to any combinations which include this software, other than combinations with devices manufactured by or for TI (“TI Devices”).  No hardware patent is licensed hereunder.

Redistributions must preserve existing copyright notices and reproduce this license (including the above copyright notice and the disclaimer and (if applicable) source code license limitations below) in the documentation and/or other materials provided with the distribution

Redistribution and use in binary form, without modification, are permitted provided that the following conditions are met:

*	No reverse engineering, decompilation, or disassembly of this software is permitted with respect to any software provided in binary form.

*	any redistribution and use are licensed by TI for use only with TI Devices.

*	Nothing shall obligate TI to provide you with source code for the software licensed and provided to you in object code.

If software source code is provided to you, modification and redistribution of the source code are permitted provided that the following conditions are met:

*	any redistribution and use of the source code, including any resulting derivative works, are licensed by TI for use only with TI Devices.

*	any redistribution and use of any object code compiled from the source code and any resulting derivative works, are licensed by TI for use only with TI Devices.

Neither the name of Texas Instruments Incorporated nor the names of its suppliers may be used to endorse or promote products derived from this software without specific prior written permission.

DISCLAIMER.

THIS SOFTWARE IS PROVIDED BY TI AND TI’S LICENSORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL TI AND TI’S LICENSORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

</licenses>
EOF

}

sw_manifest_table_header() {
    html_out="$1"
    txt_out="$2"

    echo "
<h2><u>$3</u></h2>
<p>$4</p>
" >> ${html_out}


    cat >> ${txt_out} << EOF
<table>
<thead>
attribute = "Public"
name = "$3"
description = "$4"
</thead>

EOF
}

sw_manifest_table_footer() {
    html_out="$1"
    txt_out="$2"

    if [ ! -z $3 ]; then
        cat >> ${txt_out} << EOF
<Footnotes>
$1
</footnotes>
EOF
    fi
    cat >> ${txt_out} << EOF
</table>

EOF
}

# Create the host side toolchain components table
sw_manifest_toolchain_host() {
    sdk_path="$1"

    opkg_dir="${sdk_path}/sysroots/*86*-linux/var/lib/opkg/info"

    sw_manifest_table_header ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT} "GPL-3.0 Development Host Content" "This table describes any GPL-3.0 software being delivered that is expected to run on a Development Host, instead of the target device."

    generate_sw_manifest_table ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT} $opkg_dir "true"

    sw_manifest_table_footer ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT}

    sw_manifest_table_header ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT} "Development Host Content" "This table describes any software being delivered that is expected to run on a Development Host, instead of the target device.  Some of this software may be licensed under GPL-3.0 but it is not expected to be shipped as a product."

    generate_sw_manifest_table ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT} $opkg_dir

    sw_manifest_table_footer ${SW_MANIFEST_TOOLCHAIN_HOST_FILE} ${SW_MANIFEST_TOOLCHAIN_HOST_TEXT}
}

# Create the target side toolchain components table.  These are components on
# the host but intended for the target.
sw_manifest_toolchain_target() {
    sdk_path="$1"

    opkg_dir="${sdk_path}/sysroots/${ARMPKGARCH}*/var/lib/opkg/info"

    sw_manifest_table_header ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT} "GPL-3.0 Development Libraries Installed on Host" "This table describes GPL-3.0 software libraries and headers that are installed on the development host and used during the development of software to run on the target. Customers should be careful when linking against these libraries to make sure they are complying with the license(s) of the library"

    generate_sw_manifest_table ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT} $opkg_dir "true"

    sw_manifest_table_footer ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT}

    sw_manifest_table_header ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT} "Development Libraries Installed on Host" "This table describes software libraries and headers that are installed on the development host and used during the development of software to run on the target.  Some of this software may be licensed under GPL-3.0.  Customers should be careful when linking against these libraries to make sure they are complying with the license(s) of the library"

    generate_sw_manifest_table ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT} $opkg_dir

    sw_manifest_table_footer ${SW_MANIFEST_TOOLCHAIN_TARGET_FILE} ${SW_MANIFEST_TOOLCHAIN_TARGET_TEXT}
}

# Create the table of GPL-3.0 components found in the target file system
sw_manifest_target_gplv3() {
    opkg_dir="$1"

    sw_manifest_table_header ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} "GPL-3.0 Target Device Content" "This table describes any GPL-3.0 software being delivered that is expected to run on the target device."

    generate_sw_manifest_table ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} $opkg_dir "true"

    sw_manifest_table_footer ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT}
}

# Generate the full target file system components table.
sw_manifest_target() {
    # Extract the combined set of .control files from the TARGET_IMAGES in
    # the filesystem directory to generate the manifest.
    for image in ${TARGET_IMAGES}
    do
        # Only extract tar.gz or tar.bz2 types
        if [ -e ${IMAGE_ROOTFS}/filesystem/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.tar.xz ]
        then
            tar xJf ${IMAGE_ROOTFS}/filesystem/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.tar.xz -C ${IMAGE_ROOTFS}/filesystem --wildcards *.control
        elif [ -e ${IMAGE_ROOTFS}/filesystem/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.tar.gz ]
        then
            tar xzf ${IMAGE_ROOTFS}/filesystem/${image}${ARAGO_IMAGE_SUFFIX}-${MACHINE}.tar.gz -C ${IMAGE_ROOTFS}/filesystem --wildcards *.control
        fi
    done

    # set the opkg_dir value to look at the extracted target file system
    # image files
    opkg_dir="${IMAGE_ROOTFS}/filesystem/var/lib/opkg/info"

    sw_manifest_target_gplv3 $opkg_dir

    sw_manifest_table_header ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} "All Target Device Content" "This table describes any software being delivered that is expected to run on the target device."

    generate_sw_manifest_table ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} $opkg_dir

    sw_manifest_table_footer ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT}

    # Remove the temporary var directory that was extracted
    rm -rf ${IMAGE_ROOTFS}/filesystem/var
}

# Create the table of host components installed.
sw_manifest_host() {
    sw_manifest_table_header ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} "Additional Development Host Content" "This table describes software that is installed on the development host but is not part of the linux-devkit package.  This is usually software example sources or tools such a flash utilities."

    opkg_dir="${IMAGE_ROOTFS}/var/lib/opkg/info"

    generate_sw_manifest_table ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT} $opkg_dir

    sw_manifest_table_footer ${SW_MANIFEST_FILE} ${SW_MANIFEST_TEXT}
}

# This function expects to be passed the following parameter
#   - The location to the opkg info directory containing the control files
#     of the installed packages
# Optionally if the second parameter is set to true then only GPL-3.0 contents
# will be listed.  This is for TI SW Manifests where GPL-3.0 content is
# highlighted as a separate table.
generate_sw_manifest_table() {
    html_out="$1"
    txt_out="$2"
    control_dir="$3"
    gplv3_only="$4"

    if [ ! -d "$control_dir" ]
    then
        echo "Could not find the control directory ($control_dir)"
        return 1
    fi

    if [ "$gplv3_only" = "" ]
    then
        # The second parameter was not passed so set to false
        gplv3_only="false"
    fi

echo "
<table border=1 cellspacing=1 cellpadding=1 width=80%>
<tr bgcolor=#c0c0c0  color=white>
    <td><b>Software Name</b></td>
    <td><b>Version</b></td>
    <td><b>License</b></td>
    <td><b>Location</b></td>
    <td><b>Delivered As</b></td>
    <td><b>Modified by TI</b></td>
    <td><b>Obtained from</b></td>
</tr>
" >> ${html_out}

cat >> ${txt_out} << EOF
<tbody>
<!-- Software Name
| Version
| License Type
| Delivered As
| Modified by TI
| Location
| Obtained from -->

EOF

    control_files_there=0
    for possible_control_file in $control_dir/*.control
    do
        if [ -f $possible_control_file ]
        then
            control_files_there=1
            break
        fi
    done

    if [ $control_files_there -eq 0 ]
    then
        for pkg_idx in $control_dir/oe*; do
            package_start=`grep -n "^Package" $pkg_idx || true`

            IFS_OLD=${IFS}
            IFS="
"

            begin=""
            end=""
            for pkg in ${package_start}; do
                end=`echo $pkg | cut -d: -f1`

                if [ -z $begin ]; then
                    pkg_name=`echo $pkg | cut -d: -f3`
                    begin=`echo $pkg | cut -d: -f1`
                    continue
                fi
                cnt=$[$cnt+1]
                head -n $[$end - 1] $pkg_idx | tail -n $[$end - $begin] > ${control_dir}/${pkg_name// /}.control

                pkg_name=`echo $pkg | cut -d: -f3`
                begin=$end
            done
            if [ ! -z $end ]; then
                end=`wc -l $pkg_idx | awk '{ print $1 }'`
                end=$[$end + 1]
                tail -n $[$end - $begin] $pkg_idx > ${control_dir}/${pkg_name// /}.control
            fi

            IFS=${IFS_OLD}
        done
    fi

    for i in $control_dir/*.control
    do
        package="`cat $i | grep Package: | awk {'print $2'}`"
        version="`cat $i | grep Version: | awk {'print $2'} | cut -f1-2 -d-`"
        long_version="`cat $i | grep Version: | awk {'print $2'}`"
        license="`cat $i | grep License: | cut -d: -f2 | sed 's/|/or/g' `"
        architecture="`cat $i | grep Architecture: | awk {'print $2'}`"
        sources="`cat $i | grep Source: | cut -d ':' -f2-`"
        location="$package""_""$long_version""_""$architecture"".ipk"

        # Set the highlight color if the license in GPL-3.0.  If this is
        # a GPL-3.0 only table then skip this package.
        case "$license" in
            *GPL-3.0*)
                highlight="bgcolor=yellow" ;;
            *unknown*)
                if [ "$gplv3_only" != "true" ]
                then
                    highlight="bgcolor=yellow"
                else
                    continue
                fi
                ;;
            *)
                if [ "$gplv3_only" != "true" ]
                then
                    highlight=""
                else
                    continue
                fi
                ;;
        esac

        # source variable contains the text to be used in the manifest
        source=""
        # Are there additional files in the package that come from the
        # arago/OE metadata?
        extra_files="0"
        modified="No"
        for s in $sources
        do
            case "$s" in
                file://*)
                    extra_files="1"
                    ;;
                http://install.source.dir.local*)
                    # If we are pulling something from a local file system then
                    # it is not a public modification and this should be marked
                    # as modified.  This should not be the normal case.
                    modified="Yes"
                    ;;
                *)
                    source="$source""<a href=$s>$s</a>";;
            esac
        done

        if [ "$extra_files" = "1" ]
        then
            source="$source"" <br>Files from:<br><a href=https://git.yoctoproject.org/meta-arago>https://git.yoctoproject.org/meta-arago</a><br><a href=https://git.yoctoproject.org/meta-ti>https://git.yoctoproject.org/meta-ti</a>"
        fi

        case "$package" in
            task-*)
                continue ;;
            *-source*)
                delivered_as="Source"
                ;;
            *)
                delivered_as="Binary"
                ;;
        esac

echo "
<tr>
    <td>${package} </td>
    <td>${version}</td>
    <td $highlight>${license}</td>
    <td>${location}</td>
    <td>${delivered_as}</td>
    <td>${modified}</td>
    <td>${source}</td>
</tr>
" >> ${html_out}

cat >> ${txt_out} << EOF
| ${package} | ${version} | ${license} | ${delivered_as} | ${modified} | ${location} | ${source}
EOF
    done

    echo "</table><br><br>" >> ${html_out}

cat >> ${txt_out} << EOF
</tbody>

EOF

}

# Generate the TI SW Manifest for the SDK image
generate_sw_manifest() {
    sw_manifest_header

    if [ -f "${SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_FILE}" ]
    then
        cat ${SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_FILE} >> ${SW_MANIFEST_FILE}
    fi
    if [ -f "${SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_TEXT}" ]
    then
        cat ${SW_MANIFEST_TOOLCHAIN_DEPLOY_HOST_TEXT} >> ${SW_MANIFEST_TEXT}
    fi

    sw_manifest_target
    sw_manifest_host

    if [ -f "${SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_FILE}" ]
    then
        cat ${SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_FILE} >> ${SW_MANIFEST_FILE}
    fi
    if [ -f "${SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_TEXT}" ]
    then
        cat ${SW_MANIFEST_TOOLCHAIN_DEPLOY_TARGET_TEXT} >> ${SW_MANIFEST_TEXT}
    fi

    sw_manifest_footer
}

# Generate the TI SW Manifest for the tolchains
generate_sw_manifest_toolchain() {
    sdk_path="$1"

    mkdir -p ${SW_MANIFEST_TOOLCHAIN_DIR}

    sw_manifest_toolchain_host "${sdk_path}"
    sw_manifest_toolchain_target "${sdk_path}"
}
